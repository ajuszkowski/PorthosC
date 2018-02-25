package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;

import java.util.Objects;


public class XNullaryComputationEvent extends XComputationEventBase {

    private final XLocalMemoryUnit firstOperand;

    public XNullaryComputationEvent(XEventInfo info, XLocalMemoryUnit firstOperand) {
        super(info, firstOperand.getBitness());
        this.firstOperand = firstOperand;
    }

    public XLocalMemoryUnit getFirstOperand() {
        return firstOperand;
    }

    @Override
    public <T> T accept(XgraphVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "eval(" + getFirstOperand() + ")";
    }

    @Override
    public String getUniqueId() {
        return super.getUniqueId() + "_nulop";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XNullaryComputationEvent)) return false;
        XNullaryComputationEvent that = (XNullaryComputationEvent) o;
        return Objects.equals(getFirstOperand(), that.getFirstOperand());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getFirstOperand());
    }
}
