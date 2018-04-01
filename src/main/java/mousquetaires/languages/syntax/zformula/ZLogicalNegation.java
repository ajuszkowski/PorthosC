package mousquetaires.languages.syntax.zformula;


import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;


public final class ZLogicalNegation extends ZLogicalMultiFormula<ZLogicalFormula> implements ZLogicalFormula {

    ZLogicalNegation(ZLogicalFormula expression) {
        super(ImmutableList.of(expression));
    }

    public ZLogicalFormula getExpression() {
        return getExpressions().get(0);
    }

    @Override
    protected String operatorToString() {
        return "~";
    }

    @Override
    public <T> T accept(ZformulaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
