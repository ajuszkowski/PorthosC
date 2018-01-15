package mousquetaires.languages.syntax.xrepr.events.controlflow;

import mousquetaires.languages.syntax.xrepr.events.XEvent;
import mousquetaires.languages.converters.toxrepr.XEventInfo;


public abstract class XControlFlowEvent extends XEvent {

    XControlFlowEvent(XEventInfo info) {
        super(info);
    }
}
