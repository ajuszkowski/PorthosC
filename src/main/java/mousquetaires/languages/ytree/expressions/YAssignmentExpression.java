package mousquetaires.languages.ytree.expressions;


import mousquetaires.languages.ytree.YEntity;

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
    }

    // TODO: Add fences / atomic/ ...
    public final YExpression assignee;
    public final Operator operator;
    public final YExpression expression;

    public YAssignmentExpression(YExpression assignee, YExpression expression) {
        this(Operator.Assign, assignee, expression);
    }

    public YAssignmentExpression(Operator operator, YExpression assignee, YExpression expression) {
        this.operator = operator;
        this.assignee = assignee;
        this.expression = expression;
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
