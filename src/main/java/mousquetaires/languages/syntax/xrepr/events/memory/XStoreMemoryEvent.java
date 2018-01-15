package mousquetaires.languages.syntax.xrepr.events.memory;

import mousquetaires.languages.converters.toxrepr.XEventInfo;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XSharedMemoryUnit;


/** Write event from local memoryevents (registry, {@link XLocalMemoryUnit})
 * to the shared memoryevents ({@link XSharedMemoryUnit}) */
public class XStoreMemoryEvent extends XSharedMemoryEvent {

    public final XLocalMemoryUnit source;
    public final XSharedMemoryUnit destination;
    //public final XMemoryOrder memoryOrder;

    public XStoreMemoryEvent(XEventInfo info, XSharedMemoryUnit destination, XLocalMemoryUnit source) {
        super(info);
        this.source = source;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return destination + "<- store(" + source + ", " + /*memoryOrder +*/ ")";
    }
}
