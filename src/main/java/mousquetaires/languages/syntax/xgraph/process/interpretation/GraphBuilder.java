//package mousquetaires.languages.syntax.xgraph.process.interpretation;
//
//import com.google.common.collect.ImmutableList;
//import com.google.common.collect.ImmutableMap;
//import com.google.common.collect.Maps;
//import mousquetaires.languages.syntax.xgraph.events.XEvent;
//import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
//import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
//import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
//import mousquetaires.languages.syntax.xgraph.events.fakes.XFakeEvent;
//import mousquetaires.utils.StringUtils;
//
//import java.util.*;
//
//
//// private class, DTO builder for XProcess
//// TODO: make buitifuller
//class GraphBuilder {
//
//    private XEntryEvent entryEvent;
//    private XExitEvent exitEvent;
//    private final ImmutableList.Builder<XEvent> events; //TODO: get rid of this redundant array
//    private final HashMap<XEvent, XEvent> epsilonJumps; // TODO: immutable map? check this
//    private final HashMap<XComputationEvent, XEvent> condTrueJumps;
//    private final HashMap<XComputationEvent, XEvent> condFalseJumps;
//
//
//    public GraphBuilder() {
//        events = new ImmutableList.Builder<>();
//        epsilonJumps = new HashMap<>(); // TODO: NOT THE HASH MAP HERE ? or hashmap on event info It must store the references. Maybe another map? where key is the object id?
//        condTrueJumps = new HashMap<>();
//        condFalseJumps = new HashMap<>();
//    }
//
//    public void setEntryEvent(XEntryEvent event) {
//        assert entryEvent == null: entryEvent;
//        entryEvent = event;
//    }
//
//    public void setExitEvent(XExitEvent event) {
//        assert exitEvent == null: exitEvent;
//        exitEvent = event;
//        assert epsilonJumps.containsValue(exitEvent) ||
//                condTrueJumps.containsValue(exitEvent) ||
//                condFalseJumps.containsValue(exitEvent);
//    }
//
//    public void addEvent(XEvent event) {
//        verifyEvent(event);
//        events.add(event);
//    }
//
//    public void addLinearEdge(XEvent from, XEvent to) {
//        verifyEvent(from);
//        verifyEvent(to);
//        assert from != to: "attempt to add an linear edge to the same node " + from;
//        epsilonJumps.put(from, to);
//    }
//
//    public void addThenEdge(XComputationEvent from, XEvent to) {
//        verifyEvent(from);
//        verifyEvent(to);
//        //assert from != to: from.toString(); //allowed: `while(1);`
//        condTrueJumps.put(from, to);
//    }
//
//    public void addElseEdge(XComputationEvent from, XEvent to) {
//        verifyEvent(from);
//        verifyEvent(to);
//        assert from != to: from.toString();
//        condFalseJumps.put(from, to);
//    }
//
//    public ImmutableList<XEvent> buildEvents() {
//        return events.build();
//    }
//
//    public ImmutableMap<XEvent, XEvent> buildNextEventMap() {
//        return ImmutableMap.copyOf(epsilonJumps);
//    }
//
//    public ImmutableMap<XComputationEvent, XEvent> buildThenBranchingJumpsMap() {
//        return ImmutableMap.copyOf(condTrueJumps);
//    }
//
//    public ImmutableMap<XComputationEvent, XEvent> buildElseBranchingJumpsMap() {
//        return ImmutableMap.copyOf(condFalseJumps);
//    }
//
//    public void replaceEvent(XFakeEvent fakeEvent, XEvent replaceWithEvent) {
//        epsilonJumps.remove(fakeEvent);
//
//        boolean removed = false;
//        Set<XEvent> linearPredecessors          = Maps.filterValues(epsilonJumps, succ -> Objects.equals(succ, fakeEvent)).keySet();
//        Set<XComputationEvent> thenPredecessors = Maps.filterValues(condTrueJumps, succ -> Objects.equals(succ, fakeEvent)).keySet();
//        Set<XComputationEvent> elsePredecessors = Maps.filterValues(condFalseJumps, succ -> Objects.equals(succ, fakeEvent)).keySet();
//
//        for (XEvent linearPredecessor : linearPredecessors) {
//            addLinearEdge(linearPredecessor, replaceWithEvent);
//            removed = true;
//        }
//        for (XComputationEvent thenPredecessor : thenPredecessors) {
//            addThenEdge(thenPredecessor, replaceWithEvent);
//            removed = true;
//        }
//        for (XComputationEvent elsePredecessor : elsePredecessors) {
//            addElseEdge(elsePredecessor, replaceWithEvent);
//            removed = true;
//        }
//        assert removed: "could not find any predecessor for continueing event " + StringUtils.wrap(fakeEvent);
//    }
//
//    private void verifyEvent(XEvent event) {
//        if (event == null) {
//            throw new IllegalArgumentException("Null event");
//        }
//    }
//}
