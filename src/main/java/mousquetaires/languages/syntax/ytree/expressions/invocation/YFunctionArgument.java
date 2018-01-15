package mousquetaires.languages.syntax.ytree.expressions.invocation;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
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
