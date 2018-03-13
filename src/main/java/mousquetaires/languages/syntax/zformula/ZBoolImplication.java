package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;


// todo: make multi-argument, 'a -> b -> c = a->b /\ b->c'
public class ZBoolImplication extends ZBoolMultiFormula<ZBoolFormula> implements ZBoolFormula {

    public ZBoolImplication(ZBoolFormula leftExpression, ZBoolFormula rightExpression) {
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

    //private final ZBoolFormula leftExpression;
    //private final ZBoolFormula rightExpression;
    //
    //public ZBoolImplication(ZBoolFormula leftExpression, ZBoolFormula rightExpression) {
    //    this.leftExpression = leftExpression;
    //    this.rightExpression = rightExpression;
    //}
    //
    //public ZBoolFormula getLeftExpression() {
    //    return leftExpression;
    //}
    //
    //public ZBoolFormula getRightExpression() {
    //    return rightExpression;
    //}
    //
    //@Override
    //public String toString() {
    //    return "(" + getLeftExpression() + "->" + getRightExpression() + ")";
    //}
}
