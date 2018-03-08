package mousquetaires.languages.syntax.xgraph.events.controlflow;

import mousquetaires.languages.syntax.xgraph.events.fakes.XFakeEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


public class XJumpEvent extends XFakeEvent implements XControlFlowEvent {

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

    //@Override
    //public boolean equals(Object o) {
    //    if (this == o) return true;
    //    if (!(o instanceof XEvent)) return false;
    //    XEvent other = (XEvent) o;
    //    return other instanceof XJumpEvent &&
    //            getInfo().processId().equals(other.getInfo().processId());
    //}
    //
    ////todo: override hashcode!

}
