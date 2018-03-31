package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.languages.syntax.zformula.XZOperator;

import java.util.Objects;


public class XBinaryComputationEvent extends XUnaryComputationEvent {

    private final XLocalMemoryUnit secondOperand;

    public XBinaryComputationEvent(XEventInfo info,
                                   XZOperator operator,
                                   XLocalMemoryUnit operand1,
                                   XLocalMemoryUnit secondOperand) {
        this(info, operator, operand1, secondOperand, NON_REFERENCE_ID);
    }

    private XBinaryComputationEvent(XEventInfo info,
                                   XZOperator operator,
                                   XLocalMemoryUnit operand1,
                                   XLocalMemoryUnit secondOperand,
                                   int referenceId) {
        super(info, operator, operand1, referenceId);
        this.secondOperand = secondOperand;
    }

    public XLocalMemoryUnit getSecondOperand() {
        return secondOperand;
    }

    @Override
    public XEvent asReference(int referenceId) {
        return new XBinaryComputationEvent(getInfo(), getOperator(), getFirstOperand(), getSecondOperand(), referenceId);
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return wrapWithBracketsAndReferenceId("eval(" + getFirstOperand() + " " + getOperator() + " " + getSecondOperand() + ")");
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
