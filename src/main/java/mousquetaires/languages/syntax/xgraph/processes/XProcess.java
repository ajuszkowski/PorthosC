package mousquetaires.languages.syntax.xgraph.processes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;


public class XProcess implements XEntity {

    public final String processId;
    public final XEntryEvent entryEvent;
    public final XExitEvent exitEvent;
    /*private*/public final ImmutableList<XEvent> events;
    /*private*/public final ImmutableMap<XEvent, XEvent> nextEventMap;//next, goto jumps
    /*private*/public final ImmutableMap<XComputationEvent, XEvent> thenBranchingJumpsMap; //if(true), while(true)
    /*private*/public final ImmutableMap<XComputationEvent, XEvent> elseBranchingJumpsMap; //if(false)

    public XProcess(XProcessBuilder builder) {
        this.entryEvent = builder.buildEntryEvent();
        this.exitEvent = builder.buildExitEvent();
        this.processId = builder.buildProcessId();
        this.events = builder.buildEvents();
        this.nextEventMap = builder.buildNextEventMap();
        this.thenBranchingJumpsMap = builder.buildThenBranchingJumpsMap();
        this.elseBranchingJumpsMap = builder.buildElseBranchingJumpsMap();
    }

    public String getProcessId() {
        return processId;
    }

    @Override
    public <T> T accept(XgraphVisitor<T> visitor) {
        return visitor.visit(this);
    }


}
