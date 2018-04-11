package mousquetaires.languages.syntax.xgraph.events.fake;

import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.utils.exceptions.NotSupportedException;

import java.util.Objects;


public final class XEntryEvent extends XFakeEvent {

    public XEntryEvent(XEventInfo info) {
        super(SOURCE_NODE_REF_ID, info);
    }

    @Override
    public XEntryEvent asNodeRef(int refId) {
        throw new NotSupportedException("Entry event is a source node and cannot be a reference");
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ENTRY_" + getInfo().getProcessId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XEntryEvent)) return false;
        return Objects.equals(getInfo().getProcessId(), ((XEntryEvent) o).getInfo().getProcessId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInfo().stamplessHashCode());
    }
}
