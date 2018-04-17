package mousquetaires.languages.syntax.ytree.expressions.accesses;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YAtom;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;


public class YMemberAccessExpression extends YMultiExpression implements YAtom {

    private final String memberName;

    public YMemberAccessExpression(CodeLocation location, YAtom baseExpression, String memberName) {
        this(location, baseExpression, memberName, baseExpression.getPointerLevel());
    }

    private YMemberAccessExpression(CodeLocation location, YAtom baseExpression, String memberName, int pointerLevel) {
        super(location, pointerLevel, baseExpression);
        this.memberName = memberName;
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
    public YMemberAccessExpression withPointerLevel(int level) {
        return new YMemberAccessExpression(codeLocation(), getBaseExpression(), getMemberName(), level);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return getBaseExpression() + "." + getMemberName(); // "." or "->"
    }
}
