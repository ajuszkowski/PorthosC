package mousquetaires.languages.syntax.zformula;


import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;


public class ZBoolNegation extends ZBoolMultiFormula<ZBoolFormula> implements ZBoolFormula {

    ZBoolNegation(ZBoolFormula expression) {
        super(ImmutableList.of(expression));
    }

    public ZBoolFormula getExpression() {
        return getExpressions().get(0);
    }

    @Override
    protected String getOperatorText() {
        return "~";
    }

    @Override
    public <T> T accept(ZformulaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
