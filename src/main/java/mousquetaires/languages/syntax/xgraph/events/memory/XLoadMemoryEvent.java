package mousquetaires.languages.syntax.xgraph.events.memory;

import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


/** Load event from shared memory ({@link XLocalMemoryUnit})
 * to local storage (registry, {@link XLocalMemoryUnit}) */
public final class XLoadMemoryEvent extends XMemoryEventBase implements XSharedMemoryEvent {

    public XLoadMemoryEvent(XEventInfo info, XLocalLvalueMemoryUnit destination, XSharedMemoryUnit source/*, XMemoryOrder memoryOrder*/) {
        super(info, destination, source);
    }

    @Override
    public XLocalLvalueMemoryUnit getDestination() {
        return (XLocalLvalueMemoryUnit) super.getDestination();
    }

    @Override
    public XSharedLvalueMemoryUnit getSource() {
        return (XSharedLvalueMemoryUnit) super.getSource();
    }

    @Override
    public XLoadMemoryEvent withInfo(XEventInfo newInfo) {
        return new XLoadMemoryEvent(newInfo, getDestination(), getSource());
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return wrapWithBracketsAndDepth("load(" + getDestination() + " := " + getSource() /*+ ", " + memoryOrder*/ + ")");
    }
}
