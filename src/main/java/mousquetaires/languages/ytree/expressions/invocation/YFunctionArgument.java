package mousquetaires.languages.ytree.expressions.invocation;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.expressions.YExpression;


public class YFunctionArgument extends YExpression {

    @Override
    public void accept(YtreeVisitor visitor) {
        visitor.visit(this);
    }
}
