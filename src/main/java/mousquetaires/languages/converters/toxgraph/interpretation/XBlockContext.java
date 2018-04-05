package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XFakeEvent;

import java.util.ArrayList;
import java.util.List;


class XBlockContext {
    /*private*/public XEvent entryEvent;

    /*private*/public final XBlockContextKind kind;
    /*private*/public XProcessInterpreter.ContextState state;
    /*private*/public XProcessInterpreter.BranchKind currentBranchKind;

    /*private*/public XComputationEvent conditionEvent;

    /*private*/public XEvent firstThenBranchEvent;
    /*private*/public XEvent firstElseBranchEvent;

    /*private*/public XEvent lastThenBranchEvent;
    /*private*/public XEvent lastElseBranchEvent;

    /*private*/public List<XFakeEvent> continueingEvents;
    /*private*/public List<XFakeEvent> breakingEvents;

    public XBlockContext(XBlockContextKind kind) {
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

    public void setState(XProcessInterpreter.ContextState state) {
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

    public void addContinueEvent(XFakeEvent previousEvent) {
        //assert event != conditionEvent;
        if (continueingEvents == null) {
            continueingEvents = new ArrayList<>();
        }
        continueingEvents.add(previousEvent);
    }

    public void addBreakEvent(XFakeEvent previousEvent) {
        //assert event != conditionEvent;
        if (breakingEvents == null) {
            breakingEvents = new ArrayList<>();
        }
        breakingEvents.add(previousEvent);
    }

    public boolean hasContinueEvents() {
        return continueingEvents != null && continueingEvents.size() > 0;
    }

    public boolean hasBreakEvents() {
        return breakingEvents != null && breakingEvents.size() > 0;
    }

    @Override
    public String toString() {
        return  entryEvent + "..." + conditionEvent + " { " + firstThenBranchEvent + "..." + lastThenBranchEvent +
                " } else { " + firstElseBranchEvent + "..." + lastElseBranchEvent + " }";
    }
}
