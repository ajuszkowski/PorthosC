package mousquetaires.languages.syntax.ytree.expressions.assignments;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariableRef;


public class YVariableAssignmentExpression extends YAssignmentExpression {

    public YVariableAssignmentExpression(YVariableRef assignee, YExpression expression) {
        super(assignee, expression);
    }

    @Override
    public YVariableRef getAssignee() {
        return (YVariableRef) super.getAssignee();
    }
}
