package mousquetaires.languages.syntax.xgraph.events.fake;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


public final class XEntryEvent extends XFakeEvent {

    public XEntryEvent(XEventInfo info) {
        super(info);
    }

    @Override
    public String getLabel() {
        return "[ENTRY+" + getInfo().getProcessId() + "]";
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XEvent)) return false;
        XEvent other = (XEvent) o;
        return other instanceof XEntryEvent &&
                getInfo().getProcessId().equals(other.getInfo().getProcessId());
    }

    //todo: override hashcode!
}
