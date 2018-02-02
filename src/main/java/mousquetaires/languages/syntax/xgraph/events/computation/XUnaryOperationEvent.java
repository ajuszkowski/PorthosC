package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;


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
}
