package mousquetaires.languages.syntax.ytree.expressions.assignments;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariable;


public class YVariableAssignmentExpression extends YAssignmentExpression {

    public YVariableAssignmentExpression(YVariable assignee, YExpression expression) {
        super(assignee, expression);
    }

    @Override
    public YVariable getAssignee() {
        return (YVariable) super.getAssignee();
    }
}
