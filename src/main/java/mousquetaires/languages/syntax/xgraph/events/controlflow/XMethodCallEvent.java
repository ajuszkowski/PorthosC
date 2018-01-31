package mousquetaires.languages.syntax.xgraph.events.controlflow;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;


public class XMethodCallEvent extends XUnconditionalJumpEvent {

    // todo
    public XMethodCallEvent(XEventInfo info, XEvent fromEvent, XEvent toEvent) {
        super(info, fromEvent, toEvent);
    }
}
