package mousquetaires.languages.syntax.xgraph.processes.contexts;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.processes.XProcessBuilder;

import java.util.ArrayList;
import java.util.List;


public class NonlinearBlock {
    /*private*/public XEvent entryEvent;

    /*private*/public final NonlinearBlockKind kind;
    /*private*/public XProcessBuilder.ContextState state;
    /*private*/public XProcessBuilder.BranchKind currentBranchKind;

    /*private*/public XComputationEvent conditionEvent;
    //private XEvent firstTrueBranchEvent;
    //private XEvent firstFalseBranchEvent;

    /*private*/public XEvent lastTrueBranchEvent;
    /*private*/public XEvent lastFalseBranchEvent;

    /*private*/public List<XEvent> continueingEvents;
    /*private*/public List<XEvent> breakingEvents;

    private boolean isInitialised;

    public NonlinearBlock(NonlinearBlockKind kind) {
        this.kind = kind;
    }

    public void setEntryEvent(XEvent entryEvent) {
        assert entryEvent != null;
        this.entryEvent = entryEvent;
    }

    public void setConditionEvent(XComputationEvent conditionEvent) {
        assert entryEvent != null;
        assert conditionEvent != null;
        this.conditionEvent = conditionEvent;
    }

    public void setState(XProcessBuilder.ContextState state) {
        this.state = state;
    }

    //public void setFirstTrueBranchEvent(XEvent event) {
    //    assert event != conditionEvent;
    //    this.firstTrueBranchEvent = event;
    //}
    //
    //public void setFirstFalseBranchEvent(XEvent event) {
    //    assert event != conditionEvent;
    //    this.firstFalseBranchEvent = event;
    //}

    public void addContinueEvent(XEvent event) {
        //assert event != conditionEvent;
        if (continueingEvents == null) {
            continueingEvents = new ArrayList<>();
        }
        continueingEvents.add(event);
    }

    public void addBreakEvent(XEvent event) {
        //assert event != conditionEvent;
        if (breakingEvents == null) {
            breakingEvents = new ArrayList<>();
        }
        breakingEvents.add(event);
    }

    public boolean hasTrueBranch() {
        return lastTrueBranchEvent != null;
    }

    public boolean hasFalseBranch() {
        return lastFalseBranchEvent != null;
    }

    public boolean hasContinueEvents() {
        return continueingEvents != null && continueingEvents.size() > 0;
    }

    public boolean hasBreakEvents() {
        return breakingEvents != null && breakingEvents.size() > 0;
    }

    @Override
    public String toString() {
        return  entryEvent + " { " + lastTrueBranchEvent + " } else { " + lastFalseBranchEvent + " }";
    }
}
