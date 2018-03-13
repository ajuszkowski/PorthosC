package mousquetaires.languages.syntax.zformula;


import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;


public class ZBoolAtom extends ZBoolMultiFormula<ZAtom> implements ZBoolFormula, ZAtom {

    private final XZOperator operator;

    public ZBoolAtom(XZOperator operator, ZAtom leftExpression, ZAtom rightExpression) {
        super(ImmutableList.of(leftExpression, rightExpression));
        this.operator = operator;
    }

    public ZAtom getLeft() {
        return getExpressions().get(0);
    }

    public ZAtom getRight() {
        return getExpressions().get(1);
    }

    @Override
    protected String getOperatorText() {
        return operator.getText();
    }
}
