package mousquetaires.languages.ytree.expressions.invocation;

import mousquetaires.languages.common.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.utils.exceptions.NotImplementedException;


public class YFunctionArgument extends YExpression {

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YEntity copy() {
        throw new NotImplementedException();
    }
}
