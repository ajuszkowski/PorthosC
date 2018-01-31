package mousquetaires.languages.syntax.xgraph.events.controlflow;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;


public class XUnconditionalJumpEvent extends XEventBase implements XControlFlowEvent {
    private final XEvent fromEvent;
    private final XEvent toEvent;

    public XUnconditionalJumpEvent(XEventInfo info, XEvent fromEvent, XEvent toEvent) {
        super(info);
        this.fromEvent = fromEvent;
        this.toEvent = toEvent;
    }

    @Override
    public XEvent getFromEvent() {
        return fromEvent;
    }

    @Override
    public XEvent getToEvent() {
        return toEvent;
    }
}
