package mousquetaires.languages.syntax.xrepr.events.controlflow;

import mousquetaires.languages.syntax.xrepr.events.XEvent;
import mousquetaires.languages.syntax.xrepr.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;


public class XConditionalJumpEvent extends XUnconditionalJumpEvent {
    public final XComputationEvent condition;

    public XConditionalJumpEvent(XEventInfo info, XComputationEvent condition, XEvent fromEvent, XEvent toEvent) {
        super(info, fromEvent, toEvent);
        this.condition = condition;
    }
}
