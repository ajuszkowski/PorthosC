package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;


public class XBinaryOperationEvent extends XUnaryOperationEvent {

    private final XLocalMemoryUnit secondOperand;

    public XBinaryOperationEvent(XEventInfo info, XOperator operator, XLocalMemoryUnit operand1, XLocalMemoryUnit secondOperand) {
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
}
