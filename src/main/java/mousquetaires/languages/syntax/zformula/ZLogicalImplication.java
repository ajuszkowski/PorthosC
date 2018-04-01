package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;


// todo: make multi-argument, 'a -> b -> c = a->b /\ b->c'
public final class ZLogicalImplication extends ZLogicalMultiFormula<ZLogicalFormula> implements ZLogicalFormula {

    ZLogicalImplication(ZLogicalFormula leftExpression, ZLogicalFormula rightExpression) {
        super(ImmutableList.of(leftExpression, rightExpression));
    }

    public ZLogicalFormula getLeftExpression() {
        return getExpressions().get(0);
    }

    public ZLogicalFormula getRightExpression() {
        return getExpressions().get(1);
    }

    @Override
    protected String operatorToString() {
        return "->";
    }

    @Override
    public <T> T accept(ZformulaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
