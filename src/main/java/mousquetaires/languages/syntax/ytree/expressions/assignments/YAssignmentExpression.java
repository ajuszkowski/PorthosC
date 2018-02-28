package mousquetaires.languages.syntax.ytree.expressions.assignments;


import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;


public class YAssignmentExpression extends YMultiExpression {
    // TODO: implement in visitor, not heres
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
    //        throw new UnsupportedOperationException();
    //    }
    //
    //    @Override
    //    public YEntity copy() {
    //        return this;  // for singletons it's safe to return the value while cloning
    //    }
    //}

    // TODO: Add fences / atomic/ ...

    public YAssignmentExpression(YAssignee assignee, YExpression expression) {
        super(assignee, expression);
    }

    public YAssignee getAssignee() {
        return (YAssignee) getElements().get(0);
    }

    public YExpression getExpression() {
        return getElements().get(1);
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom(getAssignee(), getExpression());
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YAssignmentExpression copy() {
        return new YAssignmentExpression(getAssignee(), getExpression());
    }

    @Override
    public String toString() {
        return getAssignee() + " := " + getExpression();
    }

}
