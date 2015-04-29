package com.speedment.orm.field.reference;

import com.speedment.orm.field.BasePredicate;
import com.speedment.orm.field.Operator;
import com.speedment.orm.field.PredicateBuilder;
import com.speedment.orm.field.StandardBinaryOperator;
import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author pemi
 * @param <ENTITY> Entity type
 * @param <V> Value type
 */
public class BinaryPredicateBuilder<ENTITY, V extends Comparable<V>> extends BasePredicate<ENTITY> implements PredicateBuilder {

    private final ReferenceField<ENTITY, V> field;
    private final V value;
    private final StandardBinaryOperator binaryOperator;
    private final Comparator<V> entityComparator;

    public BinaryPredicateBuilder(
            ReferenceField<ENTITY, V> field,
            V value,
            StandardBinaryOperator binaryOperator,
            Comparator<V> entityComparator
    ) {
        this.field = Objects.requireNonNull(field);
        this.value = value;
        this.entityComparator = Objects.requireNonNull(entityComparator);
        this.binaryOperator = Objects.requireNonNull(binaryOperator);
    }

    public BinaryPredicateBuilder(
            ReferenceField<ENTITY, V> field,
            V value,
            StandardBinaryOperator binaryOperator
    ) {
        this(field, value, binaryOperator, Comparator.naturalOrder());
    }

    @Override
    public boolean test(ENTITY entity) {
        return test(Objects.compare(field.getFrom(entity), getValue(), entityComparator));
    }

    public boolean test(int compare) {
        return binaryOperator.getComparator().test(compare);
    }

    @Override
    public ReferenceField<ENTITY, V> getField() {
        return field;
    }

    public V getValue() {
        return value;
    }

    @Override
    public Operator getOperator() {
        return binaryOperator;
    }

}
