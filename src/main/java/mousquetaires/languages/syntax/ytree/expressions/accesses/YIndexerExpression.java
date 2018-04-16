package mousquetaires.languages.syntax.ytree.expressions.accesses;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YAtom;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;


public class YIndexerExpression extends YMultiExpression implements YAtom {

    public YIndexerExpression(YAtom baseExpression, YExpression indexExpression) {
        super(baseExpression, indexExpression);
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
