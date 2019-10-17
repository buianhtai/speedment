package com.speedment.runtime.core.internal.db;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.db.SqlBiConsumer;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.internal.manager.sql.SqlDeleteStatement;
import com.speedment.runtime.core.internal.manager.sql.SqlInsertStatement;
import com.speedment.runtime.core.internal.manager.sql.SqlUpdateStatement;
import com.speedment.runtime.core.manager.sql.SqlStatement;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.Field;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.stream.Stream;

import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static com.speedment.runtime.core.util.DatabaseUtil.dbmsTypeOf;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

public final class DbmsOperationHandlerImpl implements DbmsOperationHandler {

    private static final int INITIAL_RETRY_COUNT = 5;
    private static final Logger LOGGER = LoggerManager.getLogger(DbmsOperationHandlerImpl.class);
    private static final Logger LOGGER_PERSIST = LoggerManager.getLogger(ApplicationBuilder.LogType.PERSIST.getLoggerName());
    private static final Logger LOGGER_UPDATE = LoggerManager.getLogger(ApplicationBuilder.LogType.UPDATE.getLoggerName());
    private static final Logger LOGGER_REMOVE = LoggerManager.getLogger(ApplicationBuilder.LogType.REMOVE.getLoggerName());
    private static final Logger LOGGER_SQL_RETRY = LoggerManager.getLogger(ApplicationBuilder.LogType.SQL_RETRY.getLoggerName());
    private static final boolean SHOW_METADATA = false; // Warning: Enabling SHOW_METADATA will make some dbmses fail on metadata (notably Oracle) because all the columns must be read in order...

    private final ConnectionPoolComponent connectionPoolComponent;
    private final DbmsHandlerComponent dbmsHandlerComponent;
    private final TransactionComponent transactionComponent;
    private final SqlBiConsumer<PreparedStatement, LongConsumer> generatedKeysHandler;
    private final AtomicBoolean closed;

    public DbmsOperationHandlerImpl(
        final ConnectionPoolComponent connectionPoolComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final TransactionComponent transactionComponent
    ) {
        this(connectionPoolComponent, dbmsHandlerComponent, transactionComponent, DbmsOperationHandlerImpl::defaultGeneratedKeys);
    }

    public DbmsOperationHandlerImpl(
        final ConnectionPoolComponent connectionPoolComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final TransactionComponent transactionComponent,
        final SqlBiConsumer<PreparedStatement, LongConsumer> generatedKeysHandler
    ) {
        this.connectionPoolComponent = requireNonNull(connectionPoolComponent);
        this.dbmsHandlerComponent = requireNonNull(dbmsHandlerComponent);
        this.transactionComponent = requireNonNull(transactionComponent);
        this.generatedKeysHandler = requireNonNull(generatedKeysHandler);
        closed = new AtomicBoolean();
    }

    @ExecuteBefore(State.STOPPED)
    public void close() {
        closed.set(true);
    }

    @Override
    public <T> Stream<T> executeQuery(Dbms dbms, String sql, List<?> values, SqlFunction<ResultSet, T> rsMapper) {
        requireNonNulls(sql, values, rsMapper);
        assertNotClosed();

        final ConnectionInfo connectionInfo = new ConnectionInfo(dbms, connectionPoolComponent, transactionComponent);
        try (
            final PreparedStatement ps = connectionInfo.connection().prepareStatement(sql, java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY)) {
            configureSelect(ps);
            connectionInfo.ifNotInTransaction(c -> c.setAutoCommit(false));
            try {
                int i = 1;
                for (final Object o : values) {
                    ps.setObject(i++, o);
                }
                try (final ResultSet rs = ps.executeQuery()) {
                    configureSelect(rs);

                    // Todo: Make a transparent stream with closeHandler added.
                    final Stream.Builder<T> streamBuilder = Stream.builder();
                    while (rs.next()) {
                        streamBuilder.add(rsMapper.apply(rs));
                    }
                    return streamBuilder.build();
                }
            } finally {
                connectionInfo.ifNotInTransaction(Connection::commit);
            }
        } catch (final SQLException sqle) {
            LOGGER.error(sqle, "Error querying " + sql);
            throw new SpeedmentException(sqle);
        } finally {
            closeQuietly(connectionInfo::close);
        }
    }

    @Override
    public <T> AsynchronousQueryResult<T> executeQueryAsync(
        final Dbms dbms,
        final String sql,
        final List<?> values,
        final SqlFunction<ResultSet, T> rsMapper,
        final ParallelStrategy parallelStrategy
    ) {
        assertNotClosed();
        return new AsynchronousQueryResultImpl<>(
            sql,
            values,
            rsMapper,
            () -> new ConnectionInfo(dbms, connectionPoolComponent, transactionComponent),
            parallelStrategy,
            this::configureSelect,
            this::configureSelect
        );
    }

    @Override
    public <ENTITY> void executeInsert(Dbms dbms, String sql, List<?> values, Collection<Field<ENTITY>> generatedKeyFields, Consumer<List<Long>> generatedKeyConsumer) throws SQLException {
        logOperation(LOGGER_PERSIST, sql, values);
        final SqlInsertStatement sqlUpdateStatement = new SqlInsertStatement(sql, values, new ArrayList<>(generatedKeyFields), generatedKeyConsumer);
        execute(dbms, singletonList(sqlUpdateStatement));
    }

    @Override
    public void executeUpdate(Dbms dbms, String sql, List<?> values) throws SQLException {
        logOperation(LOGGER_UPDATE, sql, values);
        final SqlUpdateStatement sqlUpdateStatement = new SqlUpdateStatement(sql, values);
        execute(dbms, singletonList(sqlUpdateStatement));
    }

    @Override
    public void executeDelete(Dbms dbms, String sql, List<?> values) throws SQLException {
        logOperation(LOGGER_REMOVE, sql, values);
        final SqlDeleteStatement sqlDeleteStatement = new SqlDeleteStatement(sql, values);
        execute(dbms, singletonList(sqlDeleteStatement));
    }

    @Override
    public void handleGeneratedKeys(PreparedStatement ps, LongConsumer longConsumer) throws SQLException {
        generatedKeysHandler.accept(ps, longConsumer);
    }



    @Override
    public Clob createClob(Dbms dbms) throws SQLException {
        return applyOnConnection(dbms, Connection::createClob);
    }

    @Override
    public Blob createBlob(Dbms dbms) throws SQLException {
        return applyOnConnection(dbms, Connection::createBlob);
    }

    @Override
    public NClob createNClob(Dbms dbms) throws SQLException {
        return applyOnConnection(dbms, Connection::createNClob);
    }

    @Override
    public SQLXML createSQLXML(Dbms dbms) throws SQLException {
        return applyOnConnection(dbms, Connection::createSQLXML);
    }

    @Override
    public Array createArray(Dbms dbms, String typeName, Object[] elements) throws SQLException {
        assertNotClosed();
        try (final Connection connection = connectionPoolComponent.getConnection(dbms)) {
            return connection.createArrayOf(typeName, elements);
        }
    }

    @Override
    public Struct createStruct(Dbms dbms, String typeName, Object[] attributes) throws SQLException {
        assertNotClosed();
        try (final Connection connection = connectionPoolComponent.getConnection(dbms)) {
            return connection.createStruct(typeName, attributes);
        }
    }


    /////////   Extra methods

    public String encloseField(Dbms dbms, String fieldName) {
        return dbmsTypeOf(dbmsHandlerComponent, dbms).getDatabaseNamingConvention().encloseField(fieldName);
    }

    /////////////

    // Todo: Rewrite the method below.

    private void execute(Dbms dbms, List<? extends SqlStatement> sqlStatementList) throws SQLException {
        final ConnectionInfo connectionInfo = new ConnectionInfo(dbms, connectionPoolComponent, transactionComponent);
        if (connectionInfo.isInTransaction()) {
            executeInTransaction(dbms, connectionInfo.connection(), sqlStatementList);
        } else {
            executeNotInTransaction(dbms, connectionInfo.connection(), sqlStatementList);
        }
    }

    private void executeNotInTransaction(
        final Dbms dbms,
        final Connection conn,
        final List<? extends SqlStatement> sqlStatementList
    ) throws SQLException {
        requireNonNull(dbms);
        requireNonNull(conn);
        requireNonNull(sqlStatementList);

        assertNotClosed();
        int retryCount = INITIAL_RETRY_COUNT;
        boolean transactionCompleted = false;
        do {
            final AtomicReference<SqlStatement> lastSqlStatement = new AtomicReference<>();
            try {
                conn.setAutoCommit(false);
                executeSqlStatementList(sqlStatementList, lastSqlStatement, dbms, conn);
                conn.commit();
                conn.close();
                transactionCompleted = true;
            } catch (SQLException sqlEx) {
                if (retryCount < INITIAL_RETRY_COUNT) {
                    LOGGER_SQL_RETRY.error("SqlStatementList: " + sqlStatementList);
                    LOGGER_SQL_RETRY.error("SQL: " + lastSqlStatement.get());
                    LOGGER_SQL_RETRY.error(sqlEx, sqlEx.getMessage());
                }

                final String sqlState = sqlEx.getSQLState();

                if ("08S01".equals(sqlState) || "40001".equals(sqlState)) {
                    retryCount--;
                } else {
                    throw sqlEx; // Finally will be executed...
                }
            } finally {

                if (!transactionCompleted) {
                    try {
                        // If we got here the transaction should be rolled back, as not
                        // all work has been done
                        conn.rollback();
                    } catch (SQLException sqlEx) {
                        // If we got an exception here, something
                        // pretty serious is going on
                        LOGGER.error(sqlEx, "Rollback error! connection:" + sqlEx.getMessage());
                        retryCount = 0;
                    } finally {
                        conn.close();
                    }
                }
            }
        } while (!transactionCompleted && (retryCount > 0));

        if (transactionCompleted) {
            postSuccessfulTransaction(sqlStatementList);
        }
    }

    private void executeInTransaction(
        final Dbms dbms,
        final Connection conn,
        final List<? extends SqlStatement> sqlStatementList
    ) throws SQLException {
        requireNonNull(dbms);
        requireNonNull(conn);
        requireNonNull(sqlStatementList);

        assertNotClosed();
        final AtomicReference<SqlStatement> lastSqlStatement = new AtomicReference<>();
        executeSqlStatementList(sqlStatementList, lastSqlStatement, dbms, conn);
        postSuccessfulTransaction(sqlStatementList);
    }

    private void handleSqlStatement(Dbms dbms, Connection conn, SqlInsertStatement sqlStatement) throws SQLException {
        assertNotClosed();
        try (final PreparedStatement ps = conn.prepareStatement(sqlStatement.getSql(), Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            for (Object o : sqlStatement.getValues()) {
                ps.setObject(i++, o);
            }
            ps.executeUpdate();

            handleGeneratedKeys(ps, sqlStatement::addGeneratedKey);
        }
    }

    private void handleSqlStatement(Dbms dbms, Connection conn, SqlUpdateStatement sqlStatement) throws SQLException {
        handleSqlStatementHelper(conn, sqlStatement);
    }

    private void handleSqlStatement(Dbms dbms, Connection conn, SqlDeleteStatement sqlStatement) throws SQLException {
        handleSqlStatementHelper(conn, sqlStatement);
    }

    private void handleSqlStatementHelper(Connection conn, SqlStatement sqlStatement) throws SQLException {
        assertNotClosed();
        try (final PreparedStatement ps = conn.prepareStatement(sqlStatement.getSql(), Statement.NO_GENERATED_KEYS)) {
            int i = 1;
            for (Object o : sqlStatement.getValues()) {
                ps.setObject(i++, o);
            }
            ps.executeUpdate();
        }
    }

    private void postSuccessfulTransaction(List<? extends SqlStatement> sqlStatementList) {
        sqlStatementList.stream()
            .filter(SqlInsertStatement.class::isInstance)
            .map(SqlInsertStatement.class::cast)
            .forEach(SqlInsertStatement::notifyGeneratedKeyListener);
    }

    private void logOperation(Logger logger, final String sql, final List<?> values) {
        logger.debug("%s, values:%s", sql, values);
    }

    private void executeSqlStatementList(List<? extends SqlStatement> sqlStatementList, AtomicReference<SqlStatement> lastSqlStatement, Dbms dbms, Connection conn) throws SQLException {
        assertNotClosed();
        for (final SqlStatement sqlStatement : sqlStatementList) {
            lastSqlStatement.set(sqlStatement);
            switch (sqlStatement.getType()) {
                case INSERT: {
                    final SqlInsertStatement s = (SqlInsertStatement) sqlStatement;
                    handleSqlStatement(dbms, conn, s);
                    break;
                }
                case UPDATE: {
                    final SqlUpdateStatement s = (SqlUpdateStatement) sqlStatement;
                    handleSqlStatement(dbms, conn, s);
                    break;
                }
                case DELETE: {
                    final SqlDeleteStatement s = (SqlDeleteStatement) sqlStatement;
                    handleSqlStatement(dbms, conn, s);
                    break;
                }
            }

        }
    }

    private <T> T applyOnConnection(Dbms dbms, SqlFunction<Connection, T> mapper) throws SQLException {
        assertNotClosed();
        try (final Connection c = connectionPoolComponent.getConnection(dbms)) {
            return mapper.apply(c);
        }
    }

    private void assertNotClosed() {
        if (closed.get()) {
            throw new IllegalStateException(
                "The " + DbmsOperationHandler.class.getSimpleName() + " " +
                    getClass().getSimpleName() + " has been closed."
            );
        }
    }

    private interface ThrowingClosable {
        void close() throws Exception;
    }

     private void closeQuietly(ThrowingClosable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    private static void defaultGeneratedKeys(PreparedStatement ps, LongConsumer longConsumer) throws SQLException {
        try (final ResultSet generatedKeys = ps.getGeneratedKeys()) {
            while (generatedKeys.next()) {
                longConsumer.accept(generatedKeys.getLong(1));
                //sqlStatement.addGeneratedKey(generatedKeys.getLong(1));
            }
        }
    }


}