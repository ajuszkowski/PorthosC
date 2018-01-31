package mousquetaires.languages.syntax.xrepr.events.controlflow;

import mousquetaires.languages.syntax.xrepr.events.XEvent;
import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;


public class XMethodCallEvent extends XUnconditionalJumpEvent {

    // todo
    public XMethodCallEvent(XEventInfo info, XEvent fromEvent, XEvent toEvent) {
        super(info, fromEvent, toEvent);
    }
}
