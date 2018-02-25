package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.events.computation.operators.XOperator;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;

import java.util.Objects;


public class XUnaryOperationEvent extends XNullaryComputationEvent {

    private final XOperator operator;

    public XUnaryOperationEvent(XEventInfo info, XOperator operator, XLocalMemoryUnit operand1) {
        super(info, operand1);
        this.operator = operator;
    }

    public XOperator getOperator() {
        return operator;
    }

    @Override
    public <T> T accept(XgraphVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "eval(" + getOperator() + " " + getFirstOperand() + ")";
    }

    @Override
    public String getUniqueId() {
        return super.getUniqueId() + "_unop";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XUnaryOperationEvent)) return false;
        if (!super.equals(o)) return false;
        XUnaryOperationEvent that = (XUnaryOperationEvent) o;
        return getOperator() == that.getOperator();
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getOperator());
    }
}
