package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;
import mousquetaires.utils.patterns.Builder;


public class ZBoolConjunctionBuilder extends Builder<ZBoolConjunction> {
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
