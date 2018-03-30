package mousquetaires.languages.syntax.xgraph.events.memory;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


/** Load event from shared memory ({@link XLocalMemoryUnit})
 * to local storage (registry, {@link XLocalMemoryUnit}) */
public final class XLoadMemoryEvent extends XMemoryEventBase implements XSharedMemoryEvent {

    public XLoadMemoryEvent(XEventInfo info, XLocalMemoryUnit destination, XSharedMemoryUnit source/*, XMemoryOrder memoryOrder*/) {
        this(info, destination, source, NON_REFERENCE_ID);
    }

    private XLoadMemoryEvent(XEventInfo info, XLocalMemoryUnit destination, XSharedMemoryUnit source, int referenceId) {
        super(info, destination, source, referenceId);
        if (destination instanceof XConstant) {
            throw new IllegalArgumentException("Memory event with assignment to " + XConstant.class.getName()
                    + " is not allowed");
        }
    }

    @Override
    public XLocalMemoryUnit getDestination() {
        return (XLocalMemoryUnit) super.getDestination();
    }

    @Override
    public XSharedMemoryUnit getSource() {
        return (XSharedMemoryUnit) super.getSource();
    }

    @Override
    public XEvent asReference(int referenceId) {
        return new XLoadMemoryEvent(getInfo(), getDestination(), getSource(), referenceId);
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return wrapWithBracketsAndReferenceId("load(" + getDestination() + " := " + getSource() /*+ ", " + memoryOrder*/ + ")");
    }

    @Override
    public String getSmtLabel() {
        return super.getSmtLabel() + "_load";
    }
}
