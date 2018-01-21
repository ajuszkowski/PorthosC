package mousquetaires.languages.syntax.xrepr.events.memory;

import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;
import mousquetaires.languages.syntax.xrepr.memories.XMemoryUnit;


public class XSharedMemoryEvent extends XMemoryEvent {

    XSharedMemoryEvent(XEventInfo info, XMemoryUnit destination, XMemoryUnit source) {
        super(info, destination, source);
    }
}
