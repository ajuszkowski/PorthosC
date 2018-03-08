package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;

import java.util.Objects;


public class XBinaryComputationEvent extends XUnaryComputationEvent {

    private final XLocalMemoryUnit secondOperand;

    public XBinaryComputationEvent(XEventInfo info, XZOperator operator, XLocalMemoryUnit operand1, XLocalMemoryUnit secondOperand) {
        super(info, operator, operand1);
        this.secondOperand = secondOperand;
    }

    public XLocalMemoryUnit getSecondOperand() {
        return secondOperand;
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
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
        if (!(o instanceof XBinaryComputationEvent)) return false;
        if (!super.equals(o)) return false;
        XBinaryComputationEvent that = (XBinaryComputationEvent) o;
        return Objects.equals(getSecondOperand(), that.getSecondOperand());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getSecondOperand());
    }
}
