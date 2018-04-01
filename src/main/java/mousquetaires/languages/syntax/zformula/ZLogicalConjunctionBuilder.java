package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;
import mousquetaires.utils.patterns.BuilderBase;


public class ZLogicalConjunctionBuilder extends BuilderBase<ZLogicalConjunction> {

    private ImmutableList.Builder<ZLogicalFormula> expressions;

    public ZLogicalConjunctionBuilder() {
        this.expressions = new ImmutableList.Builder<>();
    }

    public void addSubFormula(ZLogicalFormula expression) {
        add(expression, expressions);
    }

    @Override
    public ZLogicalConjunction build() {
        markFinished();
        return new ZLogicalConjunction(expressions.build());
    }
}
