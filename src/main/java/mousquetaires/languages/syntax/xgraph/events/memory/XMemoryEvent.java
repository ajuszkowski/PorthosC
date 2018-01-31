package mousquetaires.languages.syntax.xgraph.events.memory;

import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;


public abstract class XMemoryEvent extends XEventBase {

    private final XMemoryUnit destination;
    private final XMemoryUnit source;

    XMemoryEvent(XEventInfo info, XMemoryUnit destination, XMemoryUnit source) {
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
}
