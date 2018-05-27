package mousquetaires.languages.syntax.ytree.expressions.accesses;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YAtom;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;


public class YIndexerExpression extends YMultiExpression implements YAtom {

    public YIndexerExpression(Origin origin, YAtom baseExpression, YExpression indexExpression) {
        this(origin, baseExpression, indexExpression, baseExpression.getPointerLevel() - 1);
    }

    public YIndexerExpression(Origin origin, YAtom baseExpression, YExpression indexExpression, int pointerLevel) {
        super(origin, pointerLevel, baseExpression, indexExpression);
    }

    @Override
    public YIndexerExpression withPointerLevel(int level) {
        return new YIndexerExpression(origin(), getBaseExpression(), getIndexExpression(), level);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public YAtom getBaseExpression() {
        return (YAtom) getElements().get(0);
    }

    public YExpression getIndexExpression() {
        return getElements().get(1);
    }

    @Override
    public Kind getKind() {
        return getBaseExpression().getKind();
    }

    @Override
    public String toString() {
        return getBaseExpression() + "[" + getIndexExpression() + "]";
    }
}
