package mousquetaires.languages.syntax.ytree.expressions.accesses;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.languages.visitors.YtreeVisitor;


public class YInvocationExpression extends YMultiExpression {

    private final YExpression baseExpression;
    // TODO: process signatures! firstly only function name. -- not here, on YIndexerExpression level

    public YInvocationExpression(YExpression baseExpression, YExpression[] arguments) {
        super(arguments);
        this.baseExpression = baseExpression;
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YEntity copy() {
        return new YInvocationExpression(getBaseExpression(), getArguments());
    }

    public YExpression getBaseExpression() {
        return baseExpression;
    }

    public YExpression[] getArguments() {
        return getElements();
    }

    // todo: override hashCode?


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getBaseExpression()).append('(');
        YExpression[] arguments = getArguments();
        int lastIndex = arguments.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            sb.append(arguments[i]).append(", ");
        }
        sb.append(arguments[lastIndex]).append(')');
        return sb.toString();
    }
}
