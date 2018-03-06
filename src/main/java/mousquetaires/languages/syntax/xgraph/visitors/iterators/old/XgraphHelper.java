package mousquetaires.languages.syntax.xgraph.visitors.iterators.old;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;

import java.util.*;


public class XgraphHelper {
    private final XProcess process;
    // todo: immutable list
    private final HashMap<XEvent, List<XEvent>> predecessorsMap;

    public XgraphHelper(XProcess process) {
        this.process = process;
        predecessorsMap = collectPredecessors(process);
    }


    public boolean isBranchingEvent(XEvent event) {
        return (event instanceof XComputationEvent) &&
                process.condTrueJumps.containsKey(event);
    }

    public XEvent getEntryEvent() {
        return process.entry;
    }

    public XEvent getNextThenBranchingEvent(XEvent event) {
        return process.condTrueJumps.get(event);
    }

    public XEvent getNextElseBranchingEvent(XEvent event) {
        return process.condFalseJumps.get(event);
    }

    public XEvent getNextLinearEvent(XEvent event) {
        return process.epsilonJumps.get(event);
    }

    // todo: immutable list
    public List<XEvent> getPredecessors(XEvent event) {
        return predecessorsMap.get(event);
    }

    private HashMap<XEvent, List<XEvent>> collectPredecessors(XProcess process) {
        HashMap<XEvent, List<XEvent>> result = new HashMap<>();
        Queue<XEvent> queue = new ArrayDeque<>();
        XExitEvent exitEvent = process.exit;
        queue.add(process.entry);
        while (!queue.isEmpty()) {
            XEvent current = queue.remove();
            XEvent nextLinear = process.epsilonJumps.get(current);
            if (nextLinear != null) {
                add(nextLinear, current, result);
                queue.add(nextLinear);
            }
            else if (current != exitEvent) {
                XComputationEvent currentComputationEvent = (XComputationEvent) current;
                XEvent nextThen = process.condTrueJumps.get(currentComputationEvent);
                XEvent nextElse = process.condFalseJumps.get(currentComputationEvent);
                assert nextThen != null;
                assert nextElse != null;
                add(nextThen, current, result);
                add(nextElse, current, result);
                queue.add(nextThen);
                queue.add(nextElse);
            }
        }
        return result;
    }

    private void add(XEvent key, XEvent valueElement, HashMap<XEvent, List<XEvent>> map) {
        if (!map.containsKey(key)) {
            ArrayList<XEvent> valuesList = new ArrayList<>();
            valuesList.add(valueElement);
            map.put(key, valuesList);
        }
        else {
            map.get(key).add(valueElement);
        }
    }
}
