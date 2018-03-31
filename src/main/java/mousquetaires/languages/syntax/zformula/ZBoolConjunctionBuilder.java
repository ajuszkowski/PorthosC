package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;
import mousquetaires.utils.patterns.BuilderBase;


public class ZBoolConjunctionBuilder extends BuilderBase<ZBoolConjunction> {

    private ImmutableList.Builder<ZBoolFormula> expressions;

    public ZBoolConjunctionBuilder() {
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
