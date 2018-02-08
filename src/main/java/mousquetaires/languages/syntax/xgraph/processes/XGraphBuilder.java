package mousquetaires.languages.syntax.xgraph.processes;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XControlFlowEvent;

import java.util.HashMap;
import java.util.Map;


public class XGraphBuilder {

    private final ImmutableList.Builder<XEvent> events;
    // TODO: immutable map? check this class
    private final Map<XEvent, XEvent> nextEventMap;
    private final Map<XComputationEvent, XEvent> trueBranchingJumpsMap;
    private final Map<XComputationEvent, XEvent> falseBranchingJumpsMap;

    public XGraphBuilder() {
        events = new ImmutableList.Builder<>();
        nextEventMap = new HashMap<>(); // TODO: NOT A HASH MAP!!!!! STORE REFERENCES HERE!!!!!!
        trueBranchingJumpsMap = new HashMap<>();
        falseBranchingJumpsMap = new HashMap<>();
    }

    public void addEvent(XEvent event) {
        verifyEvent(event);
        events.add(event);
    }

    public void addEpsilonEdge(XEvent from, XEvent to) {
        assert !(from instanceof XControlFlowEvent);
        verifyEvent(from);
        verifyEvent(to);
        if (from != to) {
            nextEventMap.put(from, to);
        }
    }

    public void addTrueEdge(XComputationEvent from, XEvent to) {
        verifyEvent(from);
        verifyEvent(to);
        assert from != to: from.toString();
        trueBranchingJumpsMap.put(from, to);
    }

    public void addFalseEdge(XComputationEvent from, XEvent to) {
        verifyEvent(from);
        verifyEvent(to);
        assert from != to: from.toString();
        falseBranchingJumpsMap.put(from, to);
    }

    public void VERIFY_TEMP_METHOD() {
        // assert events also
        for (XComputationEvent trueBranchEvent : trueBranchingJumpsMap.keySet()) {
            assert !nextEventMap.containsKey(trueBranchEvent);
        }
        for (XComputationEvent falseBranchEvent : falseBranchingJumpsMap.keySet()) {
            assert !nextEventMap.containsKey(falseBranchEvent);
        }
    }

    public ImmutableList<XEvent> buildEvents() {
        return events.build();
    }

    public Map<XEvent, XEvent> buildNextEventMap() {
        return nextEventMap;
    }

    public Map<XComputationEvent, XEvent> buildTrueBranchingJumpsMap() {
        return trueBranchingJumpsMap;
    }

    public Map<XComputationEvent, XEvent> buildFalseBranchingJumpsMap() {
        return falseBranchingJumpsMap;
    }

    private void verifyEvent(XEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Null event");
        }
    }
}
