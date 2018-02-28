package mousquetaires.languages.syntax.ytree.expressions.accesses;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignee;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;


public class YMemberAccessExpression extends YMultiExpression implements YAssignee {

    private final String memberName;

    public YMemberAccessExpression(YExpression baseExpression, String memberName) {
        super(baseExpression);
        this.memberName = memberName;
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YEntity copy() {
        return new YMemberAccessExpression(getBaseExpression(), getMemberName());
    }

    public YExpression getBaseExpression() {
        return getElements().get(0);
    }

    public String getMemberName() {
        return memberName;
    }

    @Override
    public String toString() {
        return getBaseExpression() + "." + getMemberName(); // "." or "->"
    }
}
