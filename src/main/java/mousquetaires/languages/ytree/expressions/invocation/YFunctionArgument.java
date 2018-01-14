package mousquetaires.languages.ytree.expressions.invocation;

import mousquetaires.languages.common.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.utils.exceptions.NotImplementedException;


public class YFunctionArgument extends YExpression {

    public final YExpression value;

    public YFunctionArgument(YExpression value) {
        this.value = value;
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YEntity copy() {
        throw new NotImplementedException();
    }
}
