package mousquetaires.languages.syntax.xgraph.events.memory;

import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;


public abstract class XSharedMemoryEvent extends XMemoryEvent {

    XSharedMemoryEvent(XEventInfo info, XMemoryUnit destination, XMemoryUnit source) {
        super(info, destination, source);
    }
}
