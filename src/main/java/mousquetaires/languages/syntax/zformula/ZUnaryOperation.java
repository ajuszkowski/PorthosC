package mousquetaires.languages.syntax.zformula;


import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;


public final class ZUnaryOperation extends ZOperation {

    ZUnaryOperation(ZUnaryOperator operator, ZAtom operand) {
        super(operator, ImmutableList.of(operand));
    }

    public ZUnaryOperator getOperator() {
        return (ZUnaryOperator) super.getOperator();
    }

    public ZAtom getOperand() {
        return getExpressions().get(0);
    }

    @Override
    public <T> T accept(ZformulaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
