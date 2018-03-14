package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;


public class XFlowGraph extends FlowGraph<XEvent> implements XEntity {

    private final String processId;

    public XFlowGraph(String processId,
               XEvent entry,
               XExitEvent exit,
               ImmutableMap<XEvent, XEvent> edges,
               ImmutableMap<XEvent, XEvent> alternativeEdges,
               //ImmutableMap<XEvent, ImmutableSet<XEvent>> edgesReversed,
               boolean isUnrolled) {
        super(entry, exit, edges, alternativeEdges, isUnrolled);
        this.processId = processId;
    }

    public String processId() {
        return processId;
    }
}
