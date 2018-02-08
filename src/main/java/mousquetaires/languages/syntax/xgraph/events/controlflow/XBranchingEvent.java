package mousquetaires.languages.syntax.xgraph.events.controlflow;

import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;


public class XBranchingEvent extends XEventBase implements XControlFlowEvent {

    private XComputationEvent condition;

    public XBranchingEvent(XEventInfo info, XComputationEvent condition) {
        super(info);
        this.condition = condition;
    }

    @Override
    public <T> T accept(XgraphVisitor<T> visitor) {
        return visitor.visit(this);
    }

    //public boolean hasAlternativeBranch() {
    //    return getAlternativeNext() != null;
    //}

    // 'else' branch
    //public void setAlternativeNext(XEvent event) {
    //    alternativeNext = event;
    //}

    //@Nullable
    //public XEvent getAlternativeNext() {
    //    return alternativeNext;
    //}


    @Override
    public String toString() {
        return "if (" + condition + ")";
    }
}
