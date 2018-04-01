package mousquetaires.languages.syntax.xgraph.events.memory;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLocation;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


/**
 * Initial write event to any kind of memory location ({@link XMemoryUnit})
 */
public class XInitialWriteEvent extends XMemoryEventBase implements XLocalMemoryEvent {
    // implements XLocalMemoryEvent because it is not a subject for relaxations
    // TODO: rename XLocalMemoryEvent -> XNonRelaxableMemoryEvent/XStrongMemoryEvent, XSharedMemoryEvent -> XRelaxableMemoryEvent/XWeakMemoryEvent.

    public XInitialWriteEvent(XEventInfo info, XLocation destination, XConstant value) {
        super(info, destination, value);
    }

    @Override
    public XLocation getDestination() {
        return (XLocation) super.getDestination();
    }

    @Override
    public XConstant getSource() {
        return (XConstant) super.getSource();
    }

    @Override
    public String toString() {
        return "initial_write(" + getDestination() + "<- " + getSource() + ")";
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public XEvent withInfo(XEventInfo newInfo) {
        return null;
    }
}
