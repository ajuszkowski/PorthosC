package mousquetaires.languages.syntax.zformula;


import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;



public class ZBoolOperation extends ZBoolMultiFormula<ZFormula> {

    // TODO: new abstraction or string
    private final XZOperator operator;

    ZBoolOperation(XZOperator operator, ZFormula leftExpression, ZFormula rightExpression) {
        super(ImmutableList.of(leftExpression, rightExpression));
        this.operator = operator;
    }

    public XZOperator getOperator() {
        return operator;
    }

    public ZFormula getLeft() {
        return getExpressions().get(0);
    }

    public ZFormula getRight() {
        return getExpressions().get(1);
    }

    @Override
    public <T> T accept(ZformulaVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    protected String getOperatorText() {
        return operator.getText();
    }
}
