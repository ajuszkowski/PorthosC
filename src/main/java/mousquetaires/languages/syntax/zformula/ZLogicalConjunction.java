package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;


public final class ZLogicalConjunction extends ZLogicalMultiFormula<ZLogicalFormula> implements ZLogicalFormula {

    ZLogicalConjunction(ZLogicalFormula... expressions) {
        this(ImmutableList.copyOf(expressions));
    }

    ZLogicalConjunction(ImmutableList<ZLogicalFormula> expressions) {
        super(expressions);
    }


    public ImmutableList<ZLogicalFormula> getExpressions() {
        return super.getExpressions();
    }

    @Override
    protected String operatorToString() {
        return "/\\";
    }

    @Override
    public <T> T accept(ZformulaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
