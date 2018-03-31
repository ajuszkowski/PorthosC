package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;


// todo: make multi-argument, 'a -> b -> c = a->b /\ b->c'
public class ZBoolImplication extends ZBoolMultiFormula<ZBoolFormula> implements ZBoolFormula {

    ZBoolImplication(ZBoolFormula leftExpression, ZBoolFormula rightExpression) {
        super(ImmutableList.of(leftExpression, rightExpression));
    }

    public ZBoolFormula getLeftExpression() {
        return getExpressions().get(0);
    }

    public ZBoolFormula getRightExpression() {
        return getExpressions().get(1);
    }

    @Override
    protected String getOperatorText() {
        return "->";
    }


    @Override
    public <T> T accept(ZformulaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
