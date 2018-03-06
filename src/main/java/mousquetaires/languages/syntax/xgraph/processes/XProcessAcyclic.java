package mousquetaires.languages.syntax.xgraph.processes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;


public class XProcessAcyclic extends XProcess {

    private final ImmutableList<XEventLayer> distanceLayers;

    public XProcessAcyclic(String processId,
                           XEntryEvent entryEvent,
                           XExitEvent exitEvent,
                           ImmutableMap<XEvent, XEvent> epsilonJumps,
                           ImmutableMap<XComputationEvent, XEvent> trueJumps,
                           ImmutableMap<XComputationEvent, XEvent> falseJumps,
                           ImmutableList<XEventLayer> distanceLayers) {
        super(processId, entryEvent, exitEvent, epsilonJumps, trueJumps, falseJumps);
        this.distanceLayers = distanceLayers;
    }
}
