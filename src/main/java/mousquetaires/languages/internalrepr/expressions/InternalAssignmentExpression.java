package mousquetaires.languages.internalrepr.expressions;

import mousquetaires.languages.internalrepr.variables.InternalLvalueExpression;


public class InternalAssignmentExpression extends InternalExpression {

    // TODO: Add fences / atomic/ ...
    public final InternalLvalueExpression assignee;
    public final InternalExpression expression;

    public InternalAssignmentExpression(InternalLvalueExpression assignee, InternalExpression expression) {
        this.assignee = assignee;
        this.expression = expression;
    }

    public InternalAssignmentExpression withNewAssignee(InternalLvalueExpression newAssignee) {
        return new InternalAssignmentExpression(newAssignee, expression);
    }
}
