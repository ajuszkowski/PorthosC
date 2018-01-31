package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;


public class XUnaryOperationEvent extends XNullaryComputationEvent {

    private final XOperator operator;

    public XUnaryOperationEvent(XEventInfo info, XOperator operator, XLocalMemoryUnit operand1) {
        super(info, operator.getBitness(), operand1);
        this.operator = operator;
    }

    public XOperator getOperator() {
        return operator;
    }
}
