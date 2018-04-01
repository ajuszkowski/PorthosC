package mousquetaires.languages.syntax.ytree.expressions.unary;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YMemoryLocation;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignee;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;

// TODO: get rid of pointer arithmetics before constructing the Y-level!
public class YPointerUnaryExpression extends YUnaryExpression implements YAssignee, YMemoryLocation {
    public enum Kind implements YUnaryExpression.Kind {
        Reference,   // &
        Dereference, // * //called 'indirection' in C11 standard, p. 109
        ;

        @Override
        public String toString() {
            switch (this) {
                case Reference:
                    return "&";
                case Dereference:
                    return "*";
                default:
                    throw new IllegalArgumentException(this.name());
            }
        }

        @Override
        public YPointerUnaryExpression createExpression(YExpression baseExpression) {
            return new YPointerUnaryExpression(this, baseExpression);
        }
    }

    private YPointerUnaryExpression(YPointerUnaryExpression.Kind kind, YExpression expression) {
        super(kind, expression);
    }

    @Override
    public YPointerUnaryExpression.Kind getKind() {
        return (YPointerUnaryExpression.Kind) super.getKind();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YEntity copy() {
        return new YPointerUnaryExpression(getKind(), getExpression());
    }

}
