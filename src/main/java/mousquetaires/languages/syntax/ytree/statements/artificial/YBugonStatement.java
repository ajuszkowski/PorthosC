package mousquetaires.languages.syntax.ytree.statements.artificial;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;


public class YBugonStatement extends YLinearStatement {

    public YBugonStatement(YExpression expression) {
        super(expression);
    }

    @Override
    public YBugonStatement copy() {
        return new YBugonStatement(expression);
    }

    @Override
    public String toString() {
        return "bug_on(" + expression + ")";
    }
}
