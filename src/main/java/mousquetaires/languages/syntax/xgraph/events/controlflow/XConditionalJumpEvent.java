package mousquetaires.languages.syntax.xgraph.events.controlflow;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;

import javax.annotation.Nullable;


public class XConditionalJumpEvent extends XEventBase implements XControlFlowEvent {

    private XEvent alternativeNext;
    private final XComputationEvent condition;

    public XConditionalJumpEvent(XEventInfo info, XComputationEvent condition) {
        super(info);
        this.condition = condition;
    }

    public XComputationEvent getCondition() {
        return condition;
    }

    public boolean hasAlternativeBranch() {
        return getAlternativeNext() != null;
    }

    // false branch next
    public void setAlternativeNext(XEvent event) {
        alternativeNext = event;
    }

    @Nullable
    public XEvent getAlternativeNext() {
        return alternativeNext;
    }
}
