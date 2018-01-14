package mousquetaires.languages.ytree.expressions;


import mousquetaires.languages.common.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.Objects;


public class YAssignmentExpression extends YExpression {
    public enum Operator implements YEntity {
        Assign,           // '='
        MultiplyAssign,   // '*='
        DivideAssign,     // '/='
        ModuloAssign,     // '%='
        PlusAssign,       // '+='
        MinusAssign,      // '-='
        LeftShiftAssign,  // '<<='
        RightShiftAssign, // '>>='
        AndAssign,        // '&='
        OrAssign,         // '|='
        XorAssign,        // '^='
        ;

        @Override
        public Iterator<? extends YEntity> getChildrenIterator() {
            return YtreeUtils.createIteratorFrom();
        }

        @Override
        public <T> T accept(YtreeVisitor<T> visitor) {
            return visitor.visit(this);
        }

        @Override
        public YEntity copy() {
            return this;  // for singletons it's safe to return the value while cloning
        }
    }

    // TODO: Add fences / atomic/ ...
    public final YExpression assignee;
    public final Operator operator;
    public final YExpression expression;

    public YAssignmentExpression(YExpression assignee, YExpression expression) {
        this(assignee, expression, Operator.Assign);
    }

    public YAssignmentExpression(YExpression assignee, YExpression expression, Operator operator) {
        this.operator = operator;
        this.assignee = assignee;
        this.expression = expression;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(assignee, expression, operator);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YAssignmentExpression copy() {
        return new YAssignmentExpression(assignee, expression, operator);
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
