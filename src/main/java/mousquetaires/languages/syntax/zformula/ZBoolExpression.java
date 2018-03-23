package mousquetaires.languages.syntax.zformula;


import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;


public class ZBoolExpression extends ZBoolMultiFormula<ZAtom> implements ZBoolFormula, ZAtom {

    // TODO: new abstraction or string
    private final XZOperator operator;

    public ZBoolExpression(XZOperator operator, ZAtom leftExpression, ZAtom rightExpression) {
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
