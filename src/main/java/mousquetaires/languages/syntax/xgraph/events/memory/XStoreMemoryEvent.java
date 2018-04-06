package mousquetaires.languages.syntax.xgraph.events.memory;

import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XSharedLvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


/**
 * Write event from local memory (registry, {@link XLocalMemoryUnit})
 * to the shared memory ({@link XLocalMemoryUnit})
 */
public final class XStoreMemoryEvent extends XMemoryEventBase implements XSharedMemoryEvent {

    public XStoreMemoryEvent(XEventInfo info, XSharedLvalueMemoryUnit destination, XLocalMemoryUnit source) {
        super(info, destination, source);
    }

    @Override
    public XSharedLvalueMemoryUnit getDestination() {
        return (XSharedLvalueMemoryUnit) super.getDestination();
    }

    @Override
    public XLocalMemoryUnit getSource() {
        return (XLocalMemoryUnit) super.getSource();
    }

    @Override
    public XStoreMemoryEvent withInfo(XEventInfo newInfo) {
        return new XStoreMemoryEvent(newInfo, getDestination(), getSource());
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        //return wrapWithBracketsAndDepth("store(" + getDestination() + " := " + getSource() + /*", " + memoryOrder +*/ ")");
        return "STORE_" + getDestination() + "_" + getSource();
    }
}
