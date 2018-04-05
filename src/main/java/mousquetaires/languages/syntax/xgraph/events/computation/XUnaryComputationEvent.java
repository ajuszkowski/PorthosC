package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;

import java.util.Objects;


public class XUnaryComputationEvent extends XComputationEventBase {

    private final XLocalMemoryUnit operand;

    public XUnaryComputationEvent(XEventInfo info, XUnaryOperator operator, XLocalMemoryUnit operand) {
        super(info, XTypeDeterminer.determineType(operator, operand), operator);
        this.operand = operand;
    }


    public XUnaryOperator getOperator() {
        return (XUnaryOperator) super.getOperator();
    }

    public XLocalMemoryUnit getOperand() {
        return operand;
    }

    @Override
    public XUnaryComputationEvent withInfo(XEventInfo newInfo) {
        return new XUnaryComputationEvent(newInfo, getOperator(), getOperand());
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
        return wrapWithBracketsAndDepth("eval(" + getOperator() + getOperand() + ")");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof XUnaryComputationEvent)) { return false; }
        if (!super.equals(o)) { return false; }
        XUnaryComputationEvent that = (XUnaryComputationEvent) o;
        return Objects.equals(getOperand(), that.getOperand());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getOperand());
    }
}
