package mousquetaires.languages.syntax.xrepr.processes;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.syntax.xrepr.events.XEvent;
import mousquetaires.languages.syntax.xrepr.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xrepr.memories.XMemoryUnit;

import java.util.Stack;


public class XControlFlowBuilder {
    // TODO: either move links to nodes, or store int identifiers
    private final ImmutableMap.Builder<XEvent, XEvent> epsilonEdges;
    private XEvent currentEvent;

    private final ImmutableMap.Builder<XComputationEvent, XEvent> trueEdges;
    private final ImmutableMap.Builder<XComputationEvent, XEvent> falseEdges;

    private final Stack<XComputationEvent> conditionStack;
    private boolean waitingFalseEvent;
    private boolean waitingTrueEvent;

    XControlFlowBuilder() {
        this.epsilonEdges = new ImmutableMap.Builder<>();
        this.trueEdges = new ImmutableMap.Builder<>();
        this.falseEdges = new ImmutableMap.Builder<>();
        this.conditionStack = new Stack<>();
    }

    public void processCondition(XComputationEvent condition) {
        if (condition.getBitness() != XMemoryUnit.Bitness.bit1) {
            throw new RuntimeException("Invalid condition type: " + condition.getBitness()); // TODO: special type of exception
        }
        if (waitingTrueEvent) {
            // if is first statement inside another if
            processTrueEvent(condition);
        }
        conditionStack.push(condition);
    }

    public void startTrueBranch() {
        waitingTrueEvent = true;
    }

    public void startFalseBranch() {
        if (waitingTrueEvent) {
            throw new RuntimeException("still waiting for true-branch event");
        }
        waitingFalseEvent = true;
    }

    public void processEvent(XEvent event) {
        if (waitingTrueEvent) {
            processTrueEvent(event);
        }
        else if (waitingFalseEvent) {
            processFalseEvent(event);
        }
        else {
            assert currentEvent != null;
            epsilonEdges.put(currentEvent, event);
            currentEvent = event;
        }
    }

    public void finishBranchingStatement() {
        if (waitingTrueEvent) {
            throw new RuntimeException("still waiting for true-branch event");
        }
        conditionStack.pop();
    }


    private void processTrueEvent(XEvent event) {
        trueEdges.put(closestCondition(), event);
        waitingTrueEvent = false;
    }

    private void processFalseEvent(XEvent event) {
        falseEdges.put(closestCondition(), event);
        waitingFalseEvent = false;
    }

    private XComputationEvent closestCondition() {
        return conditionStack.peek();
    }
}
