package mousquetaires.languages.syntax.xrepr.events.computation;

import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;


public class XNullaryComputationEvent extends XComputationEvent {

    private final XLocalMemoryUnit firstOperand;

    public XNullaryComputationEvent(XEventInfo info, XLocalMemoryUnit firstOperand) {
        this(info, firstOperand.getBitness(), firstOperand);
    }

    protected XNullaryComputationEvent(XEventInfo info, Bitness bitness, XLocalMemoryUnit firstOperand) {
        super(bitness, info);
        this.firstOperand = firstOperand;
    }

    public XLocalMemoryUnit getFirstOperand() {
        return firstOperand;
    }
}
