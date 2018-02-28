package mousquetaires.languages.syntax.xgraph.processes;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;


public interface XProcessBuilder {
    String buildProcessId();
    XEntryEvent buildEntryEvent();
    XExitEvent buildExitEvent();
    ImmutableMap<XEvent, XEvent> buildNextEventMap();
    ImmutableMap<XComputationEvent, XEvent> buildThenBranchingJumpsMap(); //goto, if(true), while(true)
    ImmutableMap<XComputationEvent, XEvent> buildElseBranchingJumpsMap(); //if(false)
}
