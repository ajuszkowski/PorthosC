package mousquetaires.languages.syntax.xgraph.events.fake;

import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;

import java.util.Objects;


public final class XExitEvent extends XFakeEvent {

    //private final boolean boundAchieved;

    public XExitEvent(XEventInfo info) {
        super(info);
    }

    @Override
    public XExitEvent withInfo(XEventInfo newInfo) {
        return new XExitEvent(newInfo);
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "EXIT_" + getInfo().getProcessId();
    }

    // TODO: split exit into  two types: bound-achieved / bound-not-achieved

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof XExitEvent)) { return false; }
        return Objects.equals(getInfo().getProcessId(), ((XExitEvent) o).getInfo().getProcessId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInfo().stamplessHashCode());
    }
}
