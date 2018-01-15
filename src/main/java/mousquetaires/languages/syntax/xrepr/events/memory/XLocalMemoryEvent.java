package mousquetaires.languages.syntax.xrepr.events.memory;

import mousquetaires.languages.converters.toxrepr.XEventInfo;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XValue;


/**
 * Event of writing from one local memoryevents to another (e.g. from one register to another).
 */
public class XLocalMemoryEvent extends XMemoryEvent {

    public final XLocalMemoryUnit source; // Note: source may be also XValue
    public final XLocalMemoryUnit destination;

    public XLocalMemoryEvent(XEventInfo info, XLocalMemoryUnit destination, XLocalMemoryUnit source) {
        super(info);
        if (destination instanceof XValue) {
            throw new IllegalArgumentException("Memory event with assignment to " + XValue.class.getName()
                    + " is not allowed");
        }
        this.destination = destination;
        this.source = source;
    }

    @Override
    public String toString() {
        return destination + " <- " + source;
    }
}
