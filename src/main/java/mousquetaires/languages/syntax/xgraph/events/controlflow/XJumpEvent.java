package mousquetaires.languages.syntax.xgraph.events.controlflow;

import mousquetaires.languages.syntax.xgraph.events.fake.XFakeEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


public final class XJumpEvent extends XFakeEvent implements XControlFlowEvent {

    public XJumpEvent(XEventInfo info) {
        super(info);
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "[" + getUniqueId() + "]";
    }

    @Override
    public String getUniqueId() {
        return super.getUniqueId() + "_jump";
    }

    //TODO: override hashCode() and equals()
}
