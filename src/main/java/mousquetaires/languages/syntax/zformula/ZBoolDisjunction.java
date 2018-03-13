package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;


public class ZBoolDisjunction extends ZBoolMultiFormula<ZBoolFormula> implements ZBoolFormula {

    public ZBoolDisjunction(ZBoolFormula... expressions) {
        this(ImmutableList.copyOf(expressions));
    }

    ZBoolDisjunction(ImmutableList<ZBoolFormula> expressions) {
        super(expressions);
    }

    public ImmutableList<ZBoolFormula> getExpressions() {
        return super.getExpressions();
    }

    @Override
    protected String getOperatorText() {
        return "\\/";
    }
}
