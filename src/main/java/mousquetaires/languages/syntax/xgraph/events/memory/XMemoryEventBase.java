package mousquetaires.languages.syntax.xgraph.events.memory;

import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;

import java.util.Objects;


public abstract class XMemoryEventBase extends XEventBase implements XMemoryEvent {

    private final XMemoryUnit destination;
    private final XMemoryUnit source;

    XMemoryEventBase(XEventInfo info, XMemoryUnit destination, XMemoryUnit source) {
        super(info);
        this.destination = destination;
        this.source = source;
    }

    public XMemoryUnit getDestination() {
        return destination;
    }

    public XMemoryUnit getSource() {
        return source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XMemoryEventBase)) return false;
        XMemoryEventBase that = (XMemoryEventBase) o;
        return Objects.equals(getDestination(), that.getDestination()) &&
                Objects.equals(getSource(), that.getSource());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDestination(), getSource());
    }
}
