package com.speedment.runtime.compute.internal.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.BinaryExpression;
import com.speedment.runtime.compute.expression.BinaryObjExpression;
import com.speedment.runtime.compute.expression.Expression;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Utility class used to construct expression that gives the sum of two
 * expressions.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class MultiplyUtil {

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToByte<T> first, byte second) {
        class ByteMultiplyByte extends AbstractMultiplyByte<ToByte<T>> implements ToInt<T> {
            private ByteMultiplyByte(ToByte<T> first, byte second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsByte(object) * secondInner;
            }
        }

        return new ByteMultiplyByte(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToByte<T> first, int second) {
        class ByteMultiplyInt extends AbstractMultiplyInt<ToByte<T>> implements ToInt<T> {
            private ByteMultiplyInt(ToByte<T> first, int second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsByte(object) * secondInner;
            }
        }

        return new ByteMultiplyInt(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToByte<T> first, long second) {
        class ByteMultiplyLong extends AbstractMultiplyLong<ToByte<T>> implements ToLong<T> {
            private ByteMultiplyLong(ToByte<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsByte(object) * secondInner;
            }
        }

        return new ByteMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToByte<T> first, ToByte<T> second) {
        class ByteMultiplyByte extends AbstractMultiply<ToByte<T>, ToByte<T>> implements ToInt<T> {
            private ByteMultiplyByte(ToByte<T> first, ToByte<T> second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsByte(object)
                    * secondInner.applyAsByte(object);
            }
        }

        return new ByteMultiplyByte(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToShort<T> first, byte second) {
        class ShortMultiplyShort extends AbstractMultiplyByte<ToShort<T>> implements ToInt<T> {
            private ShortMultiplyShort(ToShort<T> first, byte second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsShort(object) * secondInner;
            }
        }

        return new ShortMultiplyShort(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToShort<T> first, int second) {
        class ShortMultiplyInt extends AbstractMultiplyInt<ToShort<T>> implements ToInt<T> {
            private ShortMultiplyInt(ToShort<T> first, int second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsShort(object) * secondInner;
            }
        }

        return new ShortMultiplyInt(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToShort<T> first, long second) {
        class ShortMultiplyLong extends AbstractMultiplyLong<ToShort<T>> implements ToLong<T> {
            private ShortMultiplyLong(ToShort<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsShort(object) * secondInner;
            }
        }

        return new ShortMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToShort<T> first, ToShort<T> second) {
        class ShortMultiplyShort extends AbstractMultiply<ToShort<T>, ToShort<T>> implements ToInt<T> {
            private ShortMultiplyShort(ToShort<T> first, ToShort<T> second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsShort(object)
                    * secondInner.applyAsShort(object);
            }
        }

        return new ShortMultiplyShort(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToInt<T> first, byte second) {
        class IntMultiplyByte extends AbstractMultiplyByte<ToInt<T>> implements ToInt<T> {
            private IntMultiplyByte(ToInt<T> first, byte second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) * secondInner;
            }
        }

        return new IntMultiplyByte(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToInt<T> first, int second) {
        class IntMultiplyInt extends AbstractMultiplyInt<ToInt<T>> implements ToInt<T> {
            private IntMultiplyInt(ToInt<T> first, int second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) * secondInner;
            }
        }

        return new IntMultiplyInt(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToInt<T> first, long second) {
        class IntMultiplyLong extends AbstractMultiplyLong<ToInt<T>> implements ToLong<T> {
            private IntMultiplyLong(ToInt<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsInt(object) * secondInner;
            }
        }

        return new IntMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToInt<T> first, ToByte<T> second) {
        class IntMultiplyByte extends AbstractMultiply<ToInt<T>, ToByte<T>> implements ToInt<T> {
            private IntMultiplyByte(ToInt<T> first, ToByte<T> second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) *
                    secondInner.applyAsByte(object);
            }
        }

        return new IntMultiplyByte(first, second);
    }


    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToInt<T> multiply(ToInt<T> first, ToInt<T> second) {
        class IntMultiplyInt extends AbstractMultiply<ToInt<T>, ToInt<T>> implements ToInt<T> {
            private IntMultiplyInt(ToInt<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public int applyAsInt(T object) {
                return firstInner.applyAsInt(object) *
                    secondInner.applyAsInt(object);
            }
        }

        return new IntMultiplyInt(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToLong<T> first, byte second) {
        class LongMultiplyLong extends AbstractMultiplyByte<ToLong<T>> implements ToLong<T> {
            private LongMultiplyLong(ToLong<T> first, byte second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object) * secondInner;
            }
        }

        return new LongMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToLong<T> first, int second) {
        class LongMultiplyInt extends AbstractMultiplyInt<ToLong<T>> implements ToLong<T> {
            private LongMultiplyInt(ToLong<T> first, int second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object) * secondInner;
            }
        }

        return new LongMultiplyInt(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToLong<T> first, long second) {
        class LongMultiplyLong extends AbstractMultiplyLong<ToLong<T>> implements ToLong<T> {
            private LongMultiplyLong(ToLong<T> first, long second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object) * secondInner;
            }
        }

        return new LongMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToLong<T> first, ToInt<T> second) {
        class LongMultiplyInt extends AbstractMultiply<ToLong<T>, ToInt<T>> implements ToLong<T> {
            private LongMultiplyInt(ToLong<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object)
                    * secondInner.applyAsInt(object);
            }
        }

        return new LongMultiplyInt(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToLong<T> multiply(ToLong<T> first, ToLong<T> second) {
        class LongMultiplyLong extends AbstractMultiply<ToLong<T>, ToLong<T>> implements ToLong<T> {
            private LongMultiplyLong(ToLong<T> first, ToLong<T> second) {
                super(first, second);
            }

            @Override
            public long applyAsLong(T object) {
                return firstInner.applyAsLong(object)
                    * secondInner.applyAsLong(object);
            }
        }

        return new LongMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> multiply(ToFloat<T> first, int second) {
        class FloatMultiplyInt extends AbstractMultiplyInt<ToFloat<T>> implements ToFloat<T> {
            private FloatMultiplyInt(ToFloat<T> first, int second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object) * secondInner;
            }
        }

        return new FloatMultiplyInt(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToFloat<T> first, long second) {
        class FloatMultiplyLong extends AbstractMultiplyLong<ToFloat<T>> implements ToDouble<T> {
            private FloatMultiplyLong(ToFloat<T> first, long second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsFloat(object) * secondInner;
            }
        }

        return new FloatMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> multiply(ToFloat<T> first, float second) {
        class FloatMultiplyFloat extends AbstractMultiplyFloat<ToFloat<T>> implements ToFloat<T> {
            private FloatMultiplyFloat(ToFloat<T> first, float second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object) * secondInner;
            }
        }

        return new FloatMultiplyFloat(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> multiply(ToFloat<T> first, ToInt<T> second) {
        class FloatMultiplyInt extends AbstractMultiply<ToFloat<T>, ToInt<T>> implements ToFloat<T> {
            private FloatMultiplyInt(ToFloat<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object)
                    * secondInner.applyAsInt(object);
            }
        }

        return new FloatMultiplyInt(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToFloat<T> first, ToLong<T> second) {
        class FloatMultiplyLong extends AbstractMultiply<ToFloat<T>, ToLong<T>> implements ToDouble<T> {
            private FloatMultiplyLong(ToFloat<T> first, ToLong<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsFloat(object)
                    * secondInner.applyAsLong(object);
            }
        }

        return new FloatMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToFloat<T> multiply(ToFloat<T> first, ToFloat<T> second) {
        class FloatMultiplyFloat extends AbstractMultiply<ToFloat<T>, ToFloat<T>> implements ToFloat<T> {
            private FloatMultiplyFloat(ToFloat<T> first, ToFloat<T> second) {
                super(first, second);
            }

            @Override
            public float applyAsFloat(T object) {
                return firstInner.applyAsFloat(object)
                    * secondInner.applyAsFloat(object);
            }
        }

        return new FloatMultiplyFloat(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToDouble<T> first, int second) {
        class DoubleMultiplyInt extends AbstractMultiplyInt<ToDouble<T>> implements ToDouble<T> {
            private DoubleMultiplyInt(ToDouble<T> first, int second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object) * secondInner;
            }
        }

        return new DoubleMultiplyInt(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToDouble<T> first, long second) {
        class DoubleMultiplyLong extends AbstractMultiplyLong<ToDouble<T>> implements ToDouble<T> {
            private DoubleMultiplyLong(ToDouble<T> first, long second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object) * secondInner;
            }
        }

        return new DoubleMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the expression
     * and multiplies a constant to it.
     *
     * @param first   the first input expression
     * @param second  the second input constant
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToDouble<T> first, double second) {
        class DoubleMultiplyDouble extends AbstractMultiplyDouble<ToDouble<T>> implements ToDouble<T> {
            private DoubleMultiplyDouble(ToDouble<T> first, double second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object) * secondInner;
            }
        }

        return new DoubleMultiplyDouble(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToDouble<T> first, ToInt<T> second) {
        class DoubleMultiplyInt extends AbstractMultiply<ToDouble<T>, ToInt<T>> implements ToDouble<T> {
            private DoubleMultiplyInt(ToDouble<T> first, ToInt<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object)
                    * secondInner.applyAsInt(object);
            }
        }

        return new DoubleMultiplyInt(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToDouble<T> first, ToLong<T> second) {
        class DoubleMultiplyLong extends AbstractMultiply<ToDouble<T>, ToLong<T>> implements ToDouble<T> {
            private DoubleMultiplyLong(ToDouble<T> first, ToLong<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object)
                    * secondInner.applyAsLong(object);
            }
        }

        return new DoubleMultiplyLong(first, second);
    }

    /**
     * Creates and returns an expression that takes the result of the two
     * expressions and multiplies them to get the product.
     *
     * @param first   the first input expression
     * @param second  the second input expression
     * @param <T>     the input type
     * @return        the new expression
     */
    public static <T> ToDouble<T> multiply(ToDouble<T> first, ToDouble<T> second) {
        class DoubleMultiplyDouble extends AbstractMultiply<ToDouble<T>, ToDouble<T>> implements ToDouble<T> {
            private DoubleMultiplyDouble(ToDouble<T> first, ToDouble<T> second) {
                super(first, second);
            }

            @Override
            public double applyAsDouble(T object) {
                return firstInner.applyAsDouble(object)
                    * secondInner.applyAsDouble(object);
            }
        }

        return new DoubleMultiplyDouble(first, second);
    }

    /**
     * Abstract base for a multiply operation.
     *
     * @param <FIRST>   the first operand expression type
     * @param <SECOND>  the second operand expression type
     */
    private static abstract class AbstractMultiply<FIRST extends Expression, SECOND extends Expression>
    implements BinaryExpression<FIRST, SECOND> {

        final FIRST firstInner;
        final SECOND secondInner;

        private AbstractMultiply(FIRST first, SECOND second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = requireNonNull(second);
        }

        @Override
        public final FIRST getFirst() {
            return firstInner;
        }

        @Override
        public final SECOND getSecond() {
            return secondInner;
        }

        @Override
        public final Operator getOperator() {
            return Operator.MULTIPLY;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryExpression)) return false;
            final BinaryExpression<?, ?> that = (BinaryExpression<?, ?>) o;
            return Objects.equals(firstInner, that.getFirst()) &&
                Objects.equals(secondInner, that.getSecond()) &&
                Objects.equals(getOperator(), that.getOperator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, getOperator());
        }
    }

    /**
     * Abstract base for a multiply operation that takes a {@code byte} as the
     * second operand.
     *
     * @param <INNER>  the first operand expression type
     */
    private static abstract class AbstractMultiplyByte<INNER extends Expression>
        implements BinaryObjExpression<INNER, Byte> {

        final INNER firstInner;
        final byte secondInner;

        private AbstractMultiplyByte(INNER first, byte second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = second;
        }

        @Override
        public final INNER getFirst() {
            return firstInner;
        }

        @Override
        public final Byte getSecond() {
            return secondInner;
        }

        @Override
        public final Operator getOperator() {
            return Operator.MULTIPLY;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?> that = (BinaryObjExpression<?, ?>) o;
            return Objects.equals(firstInner, that.getFirst()) &&
                Objects.equals(secondInner, that.getSecond()) &&
                Objects.equals(getOperator(), that.getOperator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, getOperator());
        }
    }

    /**
     * Abstract base for a multiply operation that takes an {@code int} as the
     * second operand.
     *
     * @param <INNER>  the first operand expression type
     */
    private static abstract class AbstractMultiplyInt<INNER extends Expression>
        implements BinaryObjExpression<INNER, Integer> {

        final INNER firstInner;
        final int secondInner;

        private AbstractMultiplyInt(INNER first, int second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = second;
        }

        @Override
        public final INNER getFirst() {
            return firstInner;
        }

        @Override
        public final Integer getSecond() {
            return secondInner;
        }

        @Override
        public final Operator getOperator() {
            return Operator.MULTIPLY;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?> that = (BinaryObjExpression<?, ?>) o;
            return Objects.equals(firstInner, that.getFirst()) &&
                Objects.equals(secondInner, that.getSecond()) &&
                Objects.equals(getOperator(), that.getOperator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, getOperator());
        }
    }

    /**
     * Abstract base for a multiply operation that takes a {@code long} as the
     * second operand.
     *
     * @param <INNER>  the first operand expression type
     */
    private static abstract class AbstractMultiplyLong<INNER extends Expression>
        implements BinaryObjExpression<INNER, Long> {

        final INNER firstInner;
        final long secondInner;

        private AbstractMultiplyLong(INNER first, long second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = second;
        }

        @Override
        public final INNER getFirst() {
            return firstInner;
        }

        @Override
        public final Long getSecond() {
            return secondInner;
        }

        @Override
        public final Operator getOperator() {
            return Operator.MULTIPLY;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?> that = (BinaryObjExpression<?, ?>) o;
            return Objects.equals(firstInner, that.getFirst()) &&
                Objects.equals(secondInner, that.getSecond()) &&
                Objects.equals(getOperator(), that.getOperator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, getOperator());
        }
    }

    /**
     * Abstract base for a multiply operation that takes a {@code float} as the
     * second operand.
     *
     * @param <INNER>  the first operand expression type
     */
    private static abstract class AbstractMultiplyFloat<INNER extends Expression>
        implements BinaryObjExpression<INNER, Float> {

        final INNER firstInner;
        final float secondInner;

        private AbstractMultiplyFloat(INNER first, float second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = second;
        }

        @Override
        public final INNER getFirst() {
            return firstInner;
        }

        @Override
        public final Float getSecond() {
            return secondInner;
        }

        @Override
        public final Operator getOperator() {
            return Operator.MULTIPLY;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?> that = (BinaryObjExpression<?, ?>) o;
            return Objects.equals(firstInner, that.getFirst()) &&
                Objects.equals(secondInner, that.getSecond()) &&
                Objects.equals(getOperator(), that.getOperator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, getOperator());
        }
    }

    /**
     * Abstract base for a multiply operation that takes a {@code double} as the
     * second operand.
     *
     * @param <INNER>  the first operand expression type
     */
    private static abstract class AbstractMultiplyDouble<INNER extends Expression>
        implements BinaryObjExpression<INNER, Double> {

        final INNER firstInner;
        final double secondInner;

        private AbstractMultiplyDouble(INNER first, double second) {
            this.firstInner  = requireNonNull(first);
            this.secondInner = second;
        }

        @Override
        public final INNER getFirst() {
            return firstInner;
        }

        @Override
        public final Double getSecond() {
            return secondInner;
        }

        @Override
        public final Operator getOperator() {
            return Operator.MULTIPLY;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?> that = (BinaryObjExpression<?, ?>) o;
            return Objects.equals(firstInner, that.getFirst()) &&
                Objects.equals(secondInner, that.getSecond()) &&
                Objects.equals(getOperator(), that.getOperator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(firstInner, secondInner, getOperator());
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private MultiplyUtil() {}
}
