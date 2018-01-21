package mousquetaires.languages.syntax.xrepr.events.computation;

import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;
import mousquetaires.types.ZType;


public class XUnaryOperationEvent extends XComputationEvent {

    private final XOperator operator;
    private final XLocalMemoryUnit operand1;

    public XUnaryOperationEvent(XEventInfo info, XOperator operator, XLocalMemoryUnit operand1) {
        this(info, operand1.getType(), operator, operand1);
    }

    protected XUnaryOperationEvent(XEventInfo info, ZType resultType, XOperator operator, XLocalMemoryUnit operand1) {
        super(info, resultType);
        this.operator = operator;
        this.operand1 = operand1;
    }

    public XLocalMemoryUnit getOperand1() {
        return operand1;
    }

    public XOperator getOperator() {
        return operator;
    }
}
