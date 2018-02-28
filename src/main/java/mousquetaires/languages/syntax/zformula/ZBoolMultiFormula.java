package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;


abstract class ZBoolMultiFormula {
    private final ImmutableList<ZBoolFormula> expressions;

    protected ZBoolMultiFormula(ImmutableList<ZBoolFormula> expressions) {
        this.expressions = expressions;
    }

    public ImmutableList<ZBoolFormula> getExpressions() {
        return expressions;
    }
}
