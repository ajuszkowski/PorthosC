package mousquetaires.languages.syntax.ytree.expressions.binary;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;


public class YIntegerBinaryExpression extends YBinaryExpression {

    private YIntegerBinaryExpression(Kind kind, YExpression leftExpression, YExpression rightExpression) {
        super(kind, leftExpression, rightExpression);
    }

    @Override
    public YIntegerBinaryExpression.Kind getKind() {
        return (YIntegerBinaryExpression.Kind) super.getKind();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public enum Kind implements YBinaryExpression.Kind {
        Plus,
        Minus,
        Multiply,
        Divide,
        Modulo,
        LeftShift,
        RightShift,
        BitAnd,
        BitOr,
        BitXor,
        ;

        @Override
        public String toString() {
            switch (this) {
                case Plus:         return "+";
                case Minus:        return "-";
                case Multiply:     return "*";
                case Divide:       return "/";
                case Modulo:       return "%";
                case LeftShift:    return "<<";
                case RightShift:   return ">>";
                case BitAnd:       return "&";
                case BitOr:        return "|";
                case BitXor:       return "^";
                default:
                    throw new IllegalArgumentException(this.name());
            }
        }

        @Override
        public YIntegerBinaryExpression createExpression(YExpression leftExpression, YExpression rightExpression) {
            return new YIntegerBinaryExpression(this, leftExpression, rightExpression);
        }
    }
}
