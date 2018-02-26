package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;

import java.util.Objects;


public class XBinaryOperationEvent extends XUnaryOperationEvent {

    private final XLocalMemoryUnit secondOperand;

    public XBinaryOperationEvent(XEventInfo info, XZOperator operator, XLocalMemoryUnit operand1, XLocalMemoryUnit secondOperand) {
        super(info, operator, operand1);
        this.secondOperand = secondOperand;
    }

    public XLocalMemoryUnit getSecondOperand() {
        return secondOperand;
    }

    @Override
    public <T> T accept(XgraphVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "eval(" + getFirstOperand() + " " + getOperator() + " " + getSecondOperand() + ")";
    }

    @Override
    public String getUniqueId() {
        return super.getUniqueId() + "_binop";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XBinaryOperationEvent)) return false;
        if (!super.equals(o)) return false;
        XBinaryOperationEvent that = (XBinaryOperationEvent) o;
        return Objects.equals(getSecondOperand(), that.getSecondOperand());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getSecondOperand());
    }
}
