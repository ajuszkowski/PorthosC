package mousquetaires.languages.syntax.ytree.expressions.assignments;


import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.Objects;


public class YAssignmentExpression implements YExpression {
    // TODO
    //public enum Kind implements YEntity {
    //    Assign,           // '='
    //    MultiplyAssign,   // '*='
    //    DivideAssign,     // '/='
    //    ModuloAssign,     // '%='
    //    PlusAssign,       // '+='
    //    MinusAssign,      // '-='
    //    LeftShiftAssign,  // '<<='
    //    RightShiftAssign, // '>>='
    //    AndAssign,        // '&='
    //    OrAssign,         // '|='
    //    XorAssign,        // '^='
    //    ;
    //
    //    @Override
    //    public Iterator<? extends YEntity> getChildrenIterator() {
    //        return YtreeUtils.createIteratorFrom();
    //    }
    //
    //    @Override
    //    public <T> T accept(YtreeVisitor<T> visitor) {
    //        throw new NotSupportedException();
    //    }
    //
    //    @Override
    //    public YEntity copy() {
    //        return this;  // for singletons it's safe to return the value while cloning
    //    }
    //}

    // TODO: Add fences / atomic/ ...
    private final YAssignee assignee;
    private final YExpression expression;

    public YAssignmentExpression(YAssignee assignee, YExpression expression) {
        this.assignee = assignee;
        this.expression = expression;
    }

    public YAssignee getAssignee() {
        return assignee;
    }

    public YExpression getExpression() {
        return expression;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(assignee, expression);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YAssignmentExpression copy() {
        return new YAssignmentExpression(assignee, expression);
    }

    @Override
    public String toString() {
        return assignee + " := " + expression;
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
