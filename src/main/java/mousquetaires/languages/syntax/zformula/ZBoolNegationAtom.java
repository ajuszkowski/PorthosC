package mousquetaires.languages.syntax.zformula;


import com.google.common.collect.ImmutableList;


public class ZBoolNegationAtom extends ZBoolMultiFormula<ZBoolFormula> implements ZBoolFormula, ZAtom {

    public ZBoolNegationAtom(ZBoolFormula expression) {
        super(ImmutableList.of(expression));
    }

    public ZBoolFormula getExpression() {
        return getExpressions().get(0);
    }

    @Override
    protected String getOperatorText() {
        return "~";
    }

    //private final ZBoolFormula expression;
    //
    //ZBoolNegationAtom(ZBoolFormula expression) {
    //    this.expression = expression;
    //}
    //
    //public ZBoolFormula getExpression() {
    //    return expression;
    //}
}
