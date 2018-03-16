package mousquetaires.languages.syntax.xgraph.events;

import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;

import java.util.Objects;


public class XEventRef implements XEvent {

    private final XEvent event;
    private final int refId;

    public XEventRef(XEvent event, int refId) {
        this.event = event;
        this.refId = refId;
    }


    public XEvent getOriginalNode() {
        return event;
    }

    public int getRefId() {
        return refId;
    }

    @Override
    public XEventInfo getInfo() {
        return null;
    }

    public String getLabel() {
        return event.getLabel() + "_" + getRefId();
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return getOriginalNode().accept(visitor); //visitor.visit(this);
    }

    @Override
    public String toString() {
        return "[" + getOriginalNode() + "," + getRefId() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XEventRef)) return false;
        XEventRef xEventRef = (XEventRef) o;
        return getRefId() == xEventRef.getRefId() &&
                Objects.equals(event, xEventRef.event);
    }

    @Override
    public int hashCode() {

        return Objects.hash(event, getRefId());
    }
}
