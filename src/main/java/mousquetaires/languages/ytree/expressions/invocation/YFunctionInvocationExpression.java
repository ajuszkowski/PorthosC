package mousquetaires.languages.ytree.expressions.invocation;

import mousquetaires.languages.common.transformers.cmin.temp.YFunctionArgumentListTemp;
import mousquetaires.languages.common.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.utils.YtreeUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;


public class YFunctionInvocationExpression extends YExpression {

    public final YExpression receiver;
    public final YFunctionArgument[] arguments;

    public YFunctionInvocationExpression(YExpression receiver, YFunctionArgumentListTemp argumentList) {
        this(receiver, argumentList.toArray(new YFunctionArgument[0]));
    }

    public YFunctionInvocationExpression(YExpression receiver, YFunctionArgument[] arguments) {
        this.receiver = receiver;
        this.arguments = arguments;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(receiver, arguments);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YFunctionInvocationExpression copy() {
        return new YFunctionInvocationExpression(receiver, arguments);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YFunctionInvocationExpression)) return false;
        YFunctionInvocationExpression that = (YFunctionInvocationExpression) o;
        return Objects.equals(receiver, that.receiver) &&
                Arrays.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(receiver);
        result = 31 * result + Arrays.hashCode(arguments);
        return result;
    }
}
