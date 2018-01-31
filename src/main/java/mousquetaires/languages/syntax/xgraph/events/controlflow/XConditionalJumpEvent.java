package mousquetaires.languages.syntax.xgraph.events.controlflow;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;


public class XConditionalJumpEvent extends XUnconditionalJumpEvent {
    public final XComputationEvent condition;

    public XConditionalJumpEvent(XEventInfo info, XComputationEvent condition, XEvent fromEvent, XEvent toEvent) {
        super(info, fromEvent, toEvent);
        this.condition = condition;
    }
}
