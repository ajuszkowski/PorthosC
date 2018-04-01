package mousquetaires.languages.syntax.zformula;


import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;


public final class ZBinaryOperation extends ZOperation {

    // TODO: new abstraction or string

    // non-recursive since x-representation
    ZBinaryOperation(ZBinaryOperator operator, ZAtom left, ZAtom right) {
        super(operator, ImmutableList.of(left, right));
    }

    public ZBinaryOperator getOperator() {
        return (ZBinaryOperator) super.getOperator();
    }

    public ZAtom getLeft() {
        return getExpressions().get(0);
    }

    public ZAtom getRight() {
        return getExpressions().get(1);
    }

    @Override
    public <T> T accept(ZformulaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
