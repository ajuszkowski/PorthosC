package mousquetaires.languages.internalrepr.expressions;


import java.util.Objects;


public class InternalAssignmentExpression extends InternalExpression {

    // TODO: Add fences / atomic/ ...
    public final InternalExpression assignee;
    public final InternalExpression expression;

    public InternalAssignmentExpression(InternalExpression assignee, InternalExpression expression) {
        this.assignee = assignee;
        this.expression = expression;
    }

    public InternalAssignmentExpression withNewAssignee(InternalExpression newAssignee) {
        return new InternalAssignmentExpression(newAssignee, expression);
    }

    @Override
    public String toString() {
        return assignee + " = " + expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InternalAssignmentExpression)) return false;
        InternalAssignmentExpression that = (InternalAssignmentExpression) o;
        return Objects.equals(assignee, that.assignee) &&
                Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {

        return Objects.hash(assignee, expression);
    }
}
