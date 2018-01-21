package mousquetaires.languages.syntax.xrepr.events.computation;

import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;


public class XBinaryOperationEvent extends XUnaryOperationEvent {

    private final XLocalMemoryUnit  operand2;

    public XBinaryOperationEvent(XEventInfo info, XOperator operator, XLocalMemoryUnit operand1, XLocalMemoryUnit operand2) {
        super(info, operator, operand1);  // TODO: type: boolean, not the 'operand1.getType()'
        this.operand2 = operand2;
    }

    public XLocalMemoryUnit getOperand2() {
        return operand2;
    }
}
