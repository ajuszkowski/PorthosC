package mousquetaires.languages.ytree.expressions;


import mousquetaires.languages.ytree.expressions.lvalue.YLvalueExpression;
import mousquetaires.languages.ytree.expressions.lvalue.YVariableRef;

import java.util.Objects;


public class YAssignmentExpression extends YExpression {

    // TODO: Add fences / atomic/ ...
    public final YLvalueExpression assignee;
    public final YExpression expression;

    public YAssignmentExpression(YLvalueExpression assignee, YExpression expression) {
        this.assignee = assignee;
        this.expression = expression;
    }

    public YAssignmentExpression withNewAssignee(YVariableRef newAssignee) {
        return new YAssignmentExpression(newAssignee, expression);
    }

    @Override
    public String toString() {
        return assignee + " = " + expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YAssignmentExpression)) return false;
        YAssignmentExpression that = (YAssignmentExpression) o;
        return Objects.equals(assignee, that.assignee) &&
                Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {

        return Objects.hash(assignee, expression);
    }
}
