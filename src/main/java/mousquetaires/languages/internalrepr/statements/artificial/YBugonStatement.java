package mousquetaires.languages.internalrepr.statements.artificial;

import mousquetaires.languages.internalrepr.expressions.YExpression;
import mousquetaires.languages.internalrepr.statements.YLinearStatement;


public class YBugonStatement extends YLinearStatement {

    public YBugonStatement(YExpression expression) {
        super(expression);
    }

    @Override
    public String toString() {
        return "bug_on(" + expression + ")";
    }
}
