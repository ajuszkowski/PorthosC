package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;


public class ZBoolConjunction extends ZBoolMultiFormula<ZBoolFormula> implements ZBoolFormula {

    ZBoolConjunction(ZBoolFormula... expressions) {
        this(ImmutableList.copyOf(expressions));
    }

    ZBoolConjunction(ImmutableList<ZBoolFormula> expressions) {
        super(expressions);
    }


    public ImmutableList<ZBoolFormula> getExpressions() {
        return super.getExpressions();
    }

    @Override
    protected String getOperatorText() {
        return "/\\";
    }

    @Override
    public <T> T accept(ZformulaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
