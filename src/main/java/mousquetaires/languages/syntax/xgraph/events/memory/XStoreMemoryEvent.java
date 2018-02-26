package mousquetaires.languages.syntax.xgraph.events.memory;

import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;


/** Write event from local memory (registry, {@link XLocalMemoryUnit})
 * to the shared memory ({@link XLocalMemoryUnit}) */
public class XStoreMemoryEvent extends XMemoryEventBase implements XSharedMemoryEvent {

    //public final XMemoryOrder memoryOrder;

    public XStoreMemoryEvent(XEventInfo info, XSharedMemoryUnit destination, XLocalMemoryUnit source) {
        super(info, destination, source);
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
    public <T> T accept(XgraphVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return getDestination() + ":=store(" + getSource() + /*", " + memoryOrder +*/ ")";
    }

    @Override
    public String getUniqueId() {
        return super.getUniqueId() + "_store";
    }
}
