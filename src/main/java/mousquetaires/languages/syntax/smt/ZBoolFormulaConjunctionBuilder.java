package mousquetaires.languages.syntax.smt;

import com.google.common.collect.ImmutableList;
import mousquetaires.utils.patterns.Builder;


public class ZBoolFormulaConjunctionBuilder extends Builder<ZBoolFormula> {
    private ImmutableList.Builder<ZBoolFormula> expressions;

    public ZBoolFormulaConjunctionBuilder() {
        this.expressions = new ImmutableList.Builder<>();
    }

    public void addSubFormula(ZBoolFormula expression) {
        add(expression, expressions);
    }

    @Override
    public ZBoolConjunction build() {
        markFinished();
        return new ZBoolConjunction(expressions.build());
    }
}
