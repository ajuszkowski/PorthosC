package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;

import java.util.Objects;


public class XNullaryComputationEvent extends XComputationEventBase {

    private final XLocalMemoryUnit firstOperand;

    public XNullaryComputationEvent(XEventInfo info, XLocalMemoryUnit firstOperand) {
        this(info, firstOperand, NON_REFERENCE_ID);
    }

    protected XNullaryComputationEvent(XEventInfo info, XLocalMemoryUnit firstOperand, int referenceId) {
        super(info, firstOperand.getBitness(), referenceId);
        this.firstOperand = firstOperand;
    }

    public XLocalMemoryUnit getFirstOperand() {
        return firstOperand;
    }

    @Override
    public XEvent asReference(int referenceId) {
        return new XNullaryComputationEvent(getInfo(), getFirstOperand(), referenceId);
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <T> T accept(XMemoryUnitVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return wrapWithBracketsAndReferenceId("eval(" + getFirstOperand() + ")");
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
