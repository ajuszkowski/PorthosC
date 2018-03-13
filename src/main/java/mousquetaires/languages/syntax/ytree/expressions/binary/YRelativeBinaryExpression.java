package mousquetaires.languages.syntax.ytree.expressions.binary;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;


public class YRelativeBinaryExpression extends YBinaryExpression {

    protected YRelativeBinaryExpression(Kind operator, YExpression leftExpression, YExpression rightExpression) {
        super(operator, leftExpression, rightExpression);
    }

    @Override
    public YRelativeBinaryExpression.Kind getKind() {
        return (YRelativeBinaryExpression.Kind) super.getKind();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YRelativeBinaryExpression copy() {
        return new YRelativeBinaryExpression(getKind(), getLeftExpression(), getRightExpression());
    }

    @Override
    public String toString() {
        return getLeftExpression() + " " + getKind() + " " + getRightExpression();
    }

    public enum Kind implements YBinaryExpression.Kind {
        Equals,
        NotEquals,
        Greater,
        GreaterOrEquals,
        Less,
        LessOrEquals,
        ;

        @Override
        public String toString() {
            switch (this) {
                case Equals:          return "==";
                case NotEquals:       return "!=";
                case Greater:         return ">";
                case GreaterOrEquals: return ">=";
                case Less:            return "<";
                case LessOrEquals:    return "<=";
                default:
                    throw new IllegalArgumentException(this.name());
            }
        }

        @Override
        public YRelativeBinaryExpression createExpression(YExpression leftExpression, YExpression rightExpression) {
            return new YRelativeBinaryExpression(this, leftExpression, rightExpression);
        }
    }
}
