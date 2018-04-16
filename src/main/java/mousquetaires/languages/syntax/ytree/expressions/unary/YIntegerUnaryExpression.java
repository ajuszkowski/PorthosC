package mousquetaires.languages.syntax.ytree.expressions.unary;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;


// we can easily avoid prefix increment/decrement while parsing
public class YIntegerUnaryExpression extends YUnaryExpression {

    /**
     * 6.5.3.3 Unary arithmetic operators
     * Constraints
     * The operand of the unary + or - operator shall have arithmetic type; of the ~ operator,
     * integer type; of the ! operator, scalar type.
     */
    public enum Kind implements YUnaryExpression.Kind {
        PrefixIncrement,    // ++x
        PrefixDecrement,    // --x
        PostfixIncrement,   // x++
        PostfixDecrement,   // x--
        IntegerNegation,    // -x
        /**
         * 6.5.3.3 Unary arithmetic operators
         * Semantics
         * The result of the ~ operator is the bitwise complement of its (promoted) operand (that is,
         * each bit in the result is set if and only if the corresponding bit in the converted operand is
         * not set). The integer promotions are performed on the operand, and the result has the
         * promoted type. If the promoted type is an unsigned type, the expression ~E is equivalent
         * to the maximum value representable in that type minus E.
         */
        BitwiseComplement,  // ~x
        ;

        @Override
        public String toString() {
            switch (this) {
                case PostfixIncrement:
                case PrefixIncrement:
                    return "++";
                case PostfixDecrement:
                case PrefixDecrement:
                    return "--";
                case IntegerNegation:
                    return "-";
                case BitwiseComplement:
                    return "~";
                default:
                    throw new IllegalArgumentException(this.name());
            }
        }

        @Override
        public <T> T accept(YtreeVisitor<T> visitor) {
            return visitor.visit(this);
        }

        @Override
        public YIntegerUnaryExpression createExpression(YExpression baseExpression) {
            return new YIntegerUnaryExpression(this, baseExpression);
        }
    }

    private YIntegerUnaryExpression(YIntegerUnaryExpression.Kind kind, YExpression expression) {
        super(kind, expression);
    }

    @Override
    public YIntegerUnaryExpression.Kind getKind() {
        return (YIntegerUnaryExpression.Kind) super.getKind();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        switch (getKind()) {
            case PrefixIncrement:
            case PrefixDecrement:
                return "" + getKind() + getExpression();
            case PostfixIncrement:
            case PostfixDecrement:
                return "" + getExpression() + getKind();
            default:
                throw new IllegalArgumentException(getKind().name());
        }
    }
}
