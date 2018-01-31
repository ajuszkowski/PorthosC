package mousquetaires.languages.syntax.xrepr.events.computation;

import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;


public class XBinaryOperationEvent extends XUnaryOperationEvent {

    private final XLocalMemoryUnit secondOperand;

    public XBinaryOperationEvent(XEventInfo info, XOperator operator, XLocalMemoryUnit operand1, XLocalMemoryUnit secondOperand) {
        super(info, operator, operand1);  // TODO: type: boolean, not the 'operand1.getType()'
        this.secondOperand = secondOperand;
    }

    public XLocalMemoryUnit getSecondOperand() {
        return secondOperand;
    }
}
