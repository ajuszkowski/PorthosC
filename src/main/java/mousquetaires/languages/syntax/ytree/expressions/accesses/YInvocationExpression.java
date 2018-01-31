package mousquetaires.languages.syntax.ytree.expressions.accesses;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.languages.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.ImmutableUtils;


public class YInvocationExpression extends YMultiExpression {

    //private final YExpression baseExpression;
    private final int elementsCount;
    // TODO: process signatures! firstly only function name. -- not here, on YIndexerExpression level

    public YInvocationExpression(YExpression baseExpression, YExpression... arguments) {
        this(baseExpression, ImmutableList.copyOf(arguments));
    }

    public YInvocationExpression(YExpression baseExpression, ImmutableList<YExpression> arguments) {
        super(ImmutableUtils.append(baseExpression, arguments));
        //this.baseExpression = baseExpression;
        this.elementsCount = arguments.size() + 1;
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
        return getElements().get(0);
    }

    public ImmutableList<YExpression> getArguments() {
        return getElements().subList(1, elementsCount);
    }

    // todo: override hashCode?

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getBaseExpression()).append('(');
        ImmutableList<YExpression> arguments = getArguments();
        int lastIndex = arguments.size() - 1;
        for (int i = 0; i < lastIndex; i++) {
            sb.append(arguments.get(i)).append(", ");
        }
        sb.append(arguments.get(lastIndex)).append(')');
        return sb.toString();
    }
}
