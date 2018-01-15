package mousquetaires.languages.syntax.xrepr.events.memory;

import mousquetaires.languages.syntax.xrepr.events.XEventInfo;
import mousquetaires.languages.syntax.xrepr.memory.XLocalMemory;


/** Event of writing from one local memory to another (e.g. from one register to another). */
public class XLocalMemoryEvent extends XMemoryEvent {
    public final XLocalMemory source;
    public final XLocalMemory destination;

    public XLocalMemoryEvent(XEventInfo info, XLocalMemory source, XLocalMemory destination) {
        super(info);
        this.source = source;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return destination + " <- " + source;
    }
}
