//package mousquetaires.languages.syntax.xgraph.visitors;
//
//import mousquetaires.languages.syntax.xgraph.events.XEvent;
//import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
//import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
//
//
//public class XProcessTraverser {
//
//    protected final XFlowGraph process;
//
//    public XProcessTraverser(XFlowGraph process) {
//        this.process = process;
//    }
//
//    public boolean hasNextEvent(XEvent event) {
//        return event != process.exit;
//        //return process.epsilonJumps.containsKey(event) ||
//        //        ((event instanceof XComputationEvent) &&
//        //                process.condTrueJumps.containsKey(event));
//        //                // || process.condFalseJumps.containsKey(event)); //unnecessary
//    }
//
//    public boolean isBranchingEvent(XEvent event) {
//        return (event instanceof XComputationEvent) &&
//                process.condTrueJumps.containsKey(event);
//    }
//
//    public XEvent nextEpsilonEvent(XEvent event) {
//        return process.epsilonJumps.get(event);
//    }
//
//    public XEvent nextCondTrueEvent(XEvent event) {
//        return process.condTrueJumps.get(event);
//    }
//
//    public XEvent nextCondFalseEvent(XEvent event) {
//        return process.condFalseJumps.get(event);
//    }
//}
