package mousquetaires.languages.syntax.smt;


import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;


public class ZBoolAtom implements ZBoolFormula, ZAtom {
    private final XZOperator operator;
    private final ZAtom leftExpression;
    private final ZAtom rightExpression;

    public ZBoolAtom(XZOperator operator, ZAtom leftExpression, ZAtom rightExpression) {
        this.operator = operator;
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public XZOperator getOperator() {
        return operator;
    }

    public ZAtom getLeftExpression() {
        return leftExpression;
    }

    public ZAtom getRightExpression() {
        return rightExpression;
    }
}
