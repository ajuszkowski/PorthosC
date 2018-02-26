package mousquetaires.languages.syntax.xgraph.processes.interpretation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.fakes.XFakeEvent;
import mousquetaires.utils.StringUtils;

import java.util.*;


class XGraphBuilder {

    /*private*/ XEntryEvent entryEvent;
    /*private*/ XExitEvent exitEvent;
    /*private*/ final ImmutableList.Builder<XEvent> events; //TODO: get rid of this redundant array
    /*private*/ final HashMap<XEvent, XEvent> nextEventMap; // TODO: immutable map? check this
    /*private*/ final HashMap<XComputationEvent, XEvent> thenBranchingJumpsMap;
    /*private*/ final HashMap<XComputationEvent, XEvent> elseBranchingJumpsMap;
    /*private*/ final HashMap<XEvent, Integer> distances;

    public XGraphBuilder() {
        events = new ImmutableList.Builder<>();
        nextEventMap = new HashMap<>(); // TODO: NOT THE HASH MAP HERE!!!!! or hashmap on event info It must store the references. Maybe another map? where key is the object id?
        thenBranchingJumpsMap = new HashMap<>();
        elseBranchingJumpsMap = new HashMap<>();
        distances = new HashMap<>();
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
        return ImmutableMap.copyOf(nextEventMap);
    }

    public ImmutableMap<XComputationEvent, XEvent> buildThenBranchingJumpsMap() {
        return ImmutableMap.copyOf(thenBranchingJumpsMap);
    }

    public ImmutableMap<XComputationEvent, XEvent> buildElseBranchingJumpsMap() {
        return ImmutableMap.copyOf(elseBranchingJumpsMap);
    }

    public void replaceEvent(XFakeEvent fakeEvent, XEvent replaceWithEvent) {
        nextEventMap.remove(fakeEvent);

        boolean removed = false;
        Set<XEvent> linearPredecessors          = Maps.filterValues(nextEventMap,          succ -> Objects.equals(succ, fakeEvent)).keySet();
        Set<XComputationEvent> thenPredecessors = Maps.filterValues(thenBranchingJumpsMap, succ -> Objects.equals(succ, fakeEvent)).keySet();
        Set<XComputationEvent> elsePredecessors = Maps.filterValues(elseBranchingJumpsMap, succ -> Objects.equals(succ, fakeEvent)).keySet();

        for (XEvent linearPredecessor : linearPredecessors) {
            addLinearEdge(linearPredecessor, replaceWithEvent);
            removed = true;
        }
        for (XComputationEvent thenPredecessor : thenPredecessors) {
            addThenEdge(thenPredecessor, replaceWithEvent);
            removed = true;
        }
        for (XComputationEvent elsePredecessor : elsePredecessors) {
            addElseEdge(elsePredecessor, replaceWithEvent);
            removed = true;
        }
        assert removed: "could not find any predecessor for continueing event " + StringUtils.wrap(fakeEvent);
    }


    private void verifyEvent(XEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Null event");
        }
    }
}
