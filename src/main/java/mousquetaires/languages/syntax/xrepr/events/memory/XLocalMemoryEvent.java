package mousquetaires.languages.syntax.xrepr.events.memory;

import mousquetaires.languages.syntax.xrepr.events.XLocalEvent;
import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XValue;


/**
 * Event of writing from one local memoryevents to another (e.g. from one register to another).
 */
public class XLocalMemoryEvent extends XMemoryEvent implements XLocalEvent {

    public XLocalMemoryEvent(XEventInfo info, XLocalMemoryUnit destination, XLocalMemoryUnit source) {
        super(info, destination, source);
        if (destination instanceof XValue) {
            throw new IllegalArgumentException("Memory event with assignment to " + XValue.class.getName()
                    + " is not allowed");
        }
    }

    @Override
    public XLocalMemoryUnit getDestination() {
        return (XLocalMemoryUnit) super.getDestination();
    }

    @Override
    public XLocalMemoryUnit getSource() {
        return (XLocalMemoryUnit) super.getSource();
    }

    @Override
    public String toString() {
        return getDestination() + " <- " + getSource();
    }
}
