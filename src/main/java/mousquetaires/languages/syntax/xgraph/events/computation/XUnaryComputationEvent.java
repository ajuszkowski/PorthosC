package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;

import java.util.Objects;


public class XUnaryComputationEvent extends XNullaryComputationEvent {

    private final XZOperator operator;

    public XUnaryComputationEvent(XEventInfo info, XZOperator operator, XLocalMemoryUnit operand1) {
        this(info, operator, operand1, NON_REFERENCE_ID);
    }

    XUnaryComputationEvent(XEventInfo info, XZOperator operator, XLocalMemoryUnit operand1, int referenceId) {
        super(info, operand1, referenceId);
        this.operator = operator;
    }

    public XZOperator getOperator() {
        return operator;
    }

    @Override
    public XEvent asReference(int referenceId) {
        return new XUnaryComputationEvent(getInfo(), getOperator(), getFirstOperand(), referenceId);
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
        return wrapWithBracketsAndReferenceId("eval(" + getOperator() + " " + getFirstOperand() + ")");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XUnaryComputationEvent)) return false;
        if (!super.equals(o)) return false;
        XUnaryComputationEvent that = (XUnaryComputationEvent) o;
        return getOperator() == that.getOperator();
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getOperator());
    }
}
