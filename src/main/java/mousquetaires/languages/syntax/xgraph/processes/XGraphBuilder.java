package mousquetaires.languages.syntax.xgraph.processes;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class XGraphBuilder {

    /*private*/ final ImmutableList.Builder<XEvent> events;
    /*private*/ final Map<XEvent, XEvent> nextEventMap; // TODO: immutable map? check this
    /*private*/ final Map<XComputationEvent, XEvent> thenBranchingJumpsMap;
    /*private*/ final Map<XComputationEvent, XEvent> elseBranchingJumpsMap;

    public XGraphBuilder() {
        events = new ImmutableList.Builder<>();
        nextEventMap = new HashMap<>(); // TODO: NOT THE HASH MAP HERE!!!!! or hashmap on event info It must store the references. Maybe another map? where key is the object id?
        thenBranchingJumpsMap = new HashMap<>();
        elseBranchingJumpsMap = new HashMap<>();
    }

    public void addEvent(XEvent event) {
        verifyEvent(event);
        events.add(event);
    }

    public void addEpsilonEdge(XEvent from, XEvent to) {
        verifyEvent(from);
        verifyEvent(to);
        assert from != to: "attempt to add an epsilon edge to the same node " + from;
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

    public void TEMP_VERIFY() {
        // assert events also
        ImmutableList<XEvent> evs = events.build();
        for (XEvent event : evs) {
            if (thenBranchingJumpsMap.containsKey(event)) {
                if (!elseBranchingJumpsMap.containsKey(event)) {
                    assert false: "then without else, src: " + event;
                }
                assert !nextEventMap.containsKey(event) : "too many srcs " + event + ": then and eps";
            }
            else if (elseBranchingJumpsMap.containsKey(event)) {
                if (!thenBranchingJumpsMap.containsKey(event)) {
                    assert false: "else without then, src: " + event + ": else and eps";
                }
            }
            else {
                if (!(event instanceof XExitEvent)) {
                    assert nextEventMap.containsKey(event) : "lack src " + event;
                }
            }
            if (!(event instanceof XEntryEvent)
                    && !nextEventMap.containsValue(event)
                    && !thenBranchingJumpsMap.containsValue(event)
                    && !elseBranchingJumpsMap.containsValue(event) ) {
                System.out.println("dead code: " + event);
            }
        }

        Set<XEvent> allEventsFromTransitions = new HashSet<>();
        allEventsFromTransitions.addAll(nextEventMap.keySet());
        allEventsFromTransitions.addAll(thenBranchingJumpsMap.keySet());
        allEventsFromTransitions.addAll(elseBranchingJumpsMap.keySet());
        allEventsFromTransitions.addAll(nextEventMap.values());
        allEventsFromTransitions.addAll(thenBranchingJumpsMap.values());
        allEventsFromTransitions.addAll(elseBranchingJumpsMap.values());
        Set<XEvent> allEventsRegistered = new HashSet<>(events.build());

        for (XEvent eventFromTransition : allEventsFromTransitions) {
            assert allEventsRegistered.contains(eventFromTransition) : "not registered event: " + eventFromTransition;
        }
        for (XEvent eventRegistered : allEventsRegistered) {
            assert allEventsFromTransitions.contains(eventRegistered) : "event without a transition: " + eventRegistered;
        }
    }

    public ImmutableList<XEvent> buildEvents() {
        return events.build();
    }

    public Map<XEvent, XEvent> buildNextEventMap() {
        return nextEventMap;
    }

    public Map<XComputationEvent, XEvent> buildTrueBranchingJumpsMap() {
        return thenBranchingJumpsMap;
    }

    public Map<XComputationEvent, XEvent> buildFalseBranchingJumpsMap() {
        return elseBranchingJumpsMap;
    }

    private void verifyEvent(XEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Null event");
        }
    }
}
