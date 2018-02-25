package mousquetaires.languages.syntax.xgraph.processes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;


public class XGraphBuilder {

    /*private*/ XEntryEvent entryEvent;
    /*private*/ XExitEvent exitEvent;
    /*private*/ final ImmutableList.Builder<XEvent> events;
    /*private*/ final ImmutableMap.Builder<XEvent, XEvent> nextEventMap; // TODO: immutable map? check this
    /*private*/ final ImmutableMap.Builder<XComputationEvent, XEvent> thenBranchingJumpsMap;
    /*private*/ final ImmutableMap.Builder<XComputationEvent, XEvent> elseBranchingJumpsMap;

    public XGraphBuilder() {
        events = new ImmutableList.Builder<>();
        nextEventMap = new ImmutableMap.Builder<>(); // TODO: NOT THE HASH MAP HERE!!!!! or hashmap on event info It must store the references. Maybe another map? where key is the object id?
        thenBranchingJumpsMap = new ImmutableMap.Builder<>();
        elseBranchingJumpsMap = new ImmutableMap.Builder<>();
    }

    public void setEntryEvent(XEntryEvent event) {
        assert entryEvent == null: entryEvent;
        entryEvent = event;
    }

    public void setExitEvent(XExitEvent event) {
        assert exitEvent == null: exitEvent;
        exitEvent = event;
    }

    public void addEvent(XEvent event) {
        verifyEvent(event);
        events.add(event);
    }

    public void addLinearEdge(XEvent from, XEvent to) {
        verifyEvent(from);
        verifyEvent(to);
        assert from != to: "attempt to add an linear edge to the same node " + from;
        nextEventMap.put(from, to);
    }

    public void addThenEdge(XComputationEvent from, XEvent to) {
        verifyEvent(from);
        verifyEvent(to);
        //assert from != to: from.toString(); //allowed: `while(1);`
        thenBranchingJumpsMap.put(from, to);
    }

    public void addElseEdge(XComputationEvent from, XEvent to) {
        verifyEvent(from);
        verifyEvent(to);
        assert from != to: from.toString();
        elseBranchingJumpsMap.put(from, to);
    }

    public ImmutableList<XEvent> buildEvents() {
        return events.build();
    }

    public ImmutableMap<XEvent, XEvent> buildNextEventMap() {
        return nextEventMap.build();
    }

    public ImmutableMap<XComputationEvent, XEvent> buildThenBranchingJumpsMap() {
        return thenBranchingJumpsMap.build();
    }

    public ImmutableMap<XComputationEvent, XEvent> buildElseBranchingJumpsMap() {
        return elseBranchingJumpsMap.build();
    }

    private void verifyEvent(XEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Null event");
        }
    }
}
