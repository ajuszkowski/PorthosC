package mousquetaires.languages.syntax.xgraph.processes;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.utils.StringUtils;


// TODO: rename to XFlowGraph (but without basic blocks)
// TODO: maybe extract class XProcessCyclic and make XProcess abstract
public class XProcess implements XEntity {

    public final String processId;
    public final XEntryEvent entry;
    public final XExitEvent exit;
    /*private*/public final ImmutableMap<XEvent, XEvent> epsilonJumps;//next, goto jumps
    /*private*/public final ImmutableMap<XComputationEvent, XEvent> condTrueJumps; //if(true), while(true)
    /*private*/public final ImmutableMap<XComputationEvent, XEvent> condFalseJumps; //if(false)

    public XProcess(String processId,
                    XEntryEvent entry,
                    XExitEvent exit,
                    ImmutableMap<XEvent, XEvent> epsilonJumps,
                    ImmutableMap<XComputationEvent, XEvent> condTrueJumps,
                    ImmutableMap<XComputationEvent, XEvent> condFalseJumps) {
        this.processId = processId;
        this.entry = entry;
        this.exit = exit;
        this.epsilonJumps = epsilonJumps;
        this.condTrueJumps = condTrueJumps;
        this.condFalseJumps = condFalseJumps;
    }

    public String getProcessId() {
        return processId;
    }

    @Override
    public String toString() {
        return "XProcess{" + "processId='" + processId + '\'' +
                ", epsilonJumps=" + StringUtils.jsonSerialize(epsilonJumps) +
                ", condTrueJumps=" + StringUtils.jsonSerialize(condTrueJumps) +
                ", condFalseJumps=" + StringUtils.jsonSerialize(condFalseJumps) +
                '}';
    }


}
