package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.common.graph.UnrolledNodesLayer;
import mousquetaires.languages.syntax.xgraph.XProcessEntity;
import mousquetaires.languages.syntax.xgraph.events.XEvent;


public final class XUnrolledProcess extends UnrolledFlowGraph<XEvent> implements XProcessEntity {

    private final String processId;

    public XUnrolledProcess(String processId,
                            XEvent source, XEvent sink,
                            ImmutableMap<XEvent, XEvent> edges,
                            ImmutableMap<XEvent, XEvent> altEdges,
                            ImmutableMap<XEvent, ImmutableSet<XEvent>> edgesReversed,
                            ImmutableMap<XEvent, ImmutableSet<XEvent>> altEdgesReversed,
                            ImmutableMap<Integer, UnrolledNodesLayer<XEvent>> layers) {
        super(source, sink, edges, altEdges, edgesReversed, altEdgesReversed, layers);
        this.processId = processId;
    }

    @Override
    public String processId() {
        return processId;
    }
}
