package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;


public class XNullaryComputationEvent extends XComputationEvent {

    private final XLocalMemoryUnit firstOperand;

    public XNullaryComputationEvent(XEventInfo info, XLocalMemoryUnit firstOperand) {
        super(info, firstOperand.getBitness());
        this.firstOperand = firstOperand;
    }

    public XLocalMemoryUnit getFirstOperand() {
        return firstOperand;
    }

    @Override
    public String toString() {
        return "eval(" + getFirstOperand() + ")";
    }
}
