package mousquetaires.languages.syntax.xrepr.events.controlflow;

import mousquetaires.languages.syntax.xrepr.events.XEventBase;
import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;


public abstract class XControlFlowEvent extends XEventBase {

    XControlFlowEvent(XEventInfo info) {
        super(info);
    }
}
