package mousquetaires.languages.syntax.xgraph.visitors.traversers;

import mousquetaires.languages.syntax.xgraph.events.XEvent;


public interface XProcessTraverser {

    boolean hasNextEvent(XEvent event);

    boolean isBranchingEvent(XEvent event);

    XEvent nextEpsilonEvent(XEvent event);

    XEvent nextCondTrueEvent(XEvent event);

    XEvent nextCondFalseEvent(XEvent event);
}
