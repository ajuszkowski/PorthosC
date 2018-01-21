package mousquetaires.languages.syntax.xrepr.events.memory;

import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XSharedMemoryUnit;


/** Write event from local memoryevents (registry, {@link XLocalMemoryUnit})
 * to the shared memoryevents ({@link XSharedMemoryUnit}) */
public class XStoreMemoryEvent extends XSharedMemoryEvent {

    //public final XMemoryOrder memoryOrder;

    public XStoreMemoryEvent(XEventInfo info, XSharedMemoryUnit destination, XLocalMemoryUnit source) {
        super(info, destination, source);
    }

    @Override
    public XSharedMemoryUnit getDestination() {
        return (XSharedMemoryUnit) super.getDestination();
    }

    @Override
    public XLocalMemoryUnit getSource() {
        return (XLocalMemoryUnit) super.getSource();
    }

    @Override
    public String toString() {
        return getDestination() + "<- store(" + getSource() + ", " + /*memoryOrder +*/ ")";
    }
}
