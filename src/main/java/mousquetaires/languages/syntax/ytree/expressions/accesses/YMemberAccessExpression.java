package mousquetaires.languages.syntax.ytree.expressions.accesses;

import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YAtom;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;


public class YMemberAccessExpression extends YMultiExpression implements YAtom {

    private final String memberName;

    public YMemberAccessExpression(YAtom baseExpression, String memberName) {
        super(baseExpression);
        this.memberName = memberName;
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public YAtom getBaseExpression() {
        return (YAtom) getElements().get(0);
    }

    public String getMemberName() {
        return memberName;
    }

    @Override
    public Kind getKind() {
        return getBaseExpression().getKind();
    }

    @Override
    public String toString() {
        return getBaseExpression() + "." + getMemberName(); // "." or "->"
    }
}
