package mousquetaires.languages.syntax.ytree.expressions.assignments;

import mousquetaires.languages.syntax.ytree.expressions.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.YVariableRef;


public class YConstantVariableAssignmentExpression extends YVariableAssignmentExpression {

    public YConstantVariableAssignmentExpression(YVariableRef assignee, YConstant expression) {
        super(assignee, expression);
    }

    @Override
    public YConstant getExpression() {
        return (YConstant) super.getExpression();
    }
}
