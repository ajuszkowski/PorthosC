package mousquetaires.languages.syntax.xgraph.visitors.traversers;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;


public class XProcessSimpleTraverser implements XProcessTraverser {

    protected final XProcess process;

    public XProcessSimpleTraverser(XProcess process) {
        this.process = process;
    }

    @Override
    public boolean hasNextEvent(XEvent event) {
        return event != process.exit;
        //return process.epsilonJumps.containsKey(event) ||
        //        ((event instanceof XComputationEvent) &&
        //                process.condTrueJumps.containsKey(event));
        //                // || process.condFalseJumps.containsKey(event)); //unnecessary
    }

    @Override
    public boolean isBranchingEvent(XEvent event) {
        return (event instanceof XComputationEvent) &&
                process.condTrueJumps.containsKey(event);
    }

    @Override
    public XEvent nextEpsilonEvent(XEvent event) {
        return process.epsilonJumps.get(event);
    }

    @Override
    public XEvent nextCondTrueEvent(XEvent event) {
        return process.condTrueJumps.get(event);
    }

    @Override
    public XEvent nextCondFalseEvent(XEvent event) {
        return process.condFalseJumps.get(event);
    }
}
