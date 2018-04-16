package mousquetaires.languages.syntax.ytree.expressions.assignments;


import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YAtom;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.exceptions.NotSupportedException;


public class YAssignmentExpression extends YMultiExpression {

    public YAssignmentExpression(YAtom assignee, YExpression expression) {
        super(assignee, expression);
    }

    public YAtom getAssignee() {
        return (YAtom) getElements().get(0);
    }

    public YExpression getExpression() {
        return getElements().get(1);
    }

    @Override
    public YExpression withPointerLevel(int level) {
        throw new NotSupportedException("assignment expression cannot be a pointer");
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return getAssignee() + " := " + getExpression();
    }

}
