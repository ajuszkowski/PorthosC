package mousquetaires.languages.xrepr.events.memory;

import mousquetaires.languages.xrepr.events.XEventInfo;
import mousquetaires.languages.xrepr.memory.XLocalMemory;
import mousquetaires.languages.xrepr.memory.XMemoryUnit;
import mousquetaires.languages.xrepr.memory.XSharedMemory;


/** Write event from local memory (registry, {@link XLocalMemory})
 * to the shared memory ({@link XSharedMemory}) */
public class XStoreEvent extends XMemoryEvent {

    public final XMemoryUnit source;
    public final XMemoryUnit destination;
    public final XMemoryOrder memoryOrder;

    public XStoreEvent(XEventInfo info, XMemoryUnit source, XMemoryUnit destination, XMemoryOrder memoryOrder) {
        super(info);
        this.source = source;
        this.destination = destination;
        this.memoryOrder = memoryOrder;
    }

    @Override
    public String toString() {
        return destination + "<- store(" + source + ", " + memoryOrder + ")";
    }
}
