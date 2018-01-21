package mousquetaires.languages.syntax.ytree.expressions.unary;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.visitors.YtreeVisitor;


// we can easily avoid prefix increment/decrement while parsing
public class YIntegerPostfixUnaryExpression extends YUnaryPostfixExpression {
    public enum Kind implements YUnaryExpression.Kind {
        Increment,    // x++
        Decrement,    // x--
        ;

        @Override
        public String toString() {
            switch (this) {
                case Increment: return "++";
                case Decrement: return "--";
                default: throw new IllegalArgumentException(this.name());
            }
        }

        @Override
        public YIntegerPostfixUnaryExpression createExpression(YExpression baseExpression) {
            return new YIntegerPostfixUnaryExpression(this, baseExpression);
        }
    }

    private YIntegerPostfixUnaryExpression(YIntegerPostfixUnaryExpression.Kind kind, YExpression expression) {
        super(kind, expression);
    }

    @Override
    public YIntegerPostfixUnaryExpression.Kind getKind() {
        return (YIntegerPostfixUnaryExpression.Kind) super.getKind();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YEntity copy() {
        return new YIntegerPostfixUnaryExpression(getKind(), getExpression());
    }
}
