package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.syntax.xgraph.events.XEvent;

import java.util.function.Predicate;


public final class XUnrolledProcess extends UnrolledFlowGraph<XEvent> {

    private final XProcessId id;

    XUnrolledProcess(XProcessId id,
                     XEvent source, XEvent sink,
                     ImmutableMap<XEvent, XEvent> edges,
                     ImmutableMap<XEvent, XEvent> altEdges,
                     ImmutableMap<XEvent, ImmutableSet<XEvent>> edgesReversed,
                     ImmutableMap<XEvent, ImmutableSet<XEvent>> altEdgesReversed,
                     ImmutableList<XEvent> nodesLinearised) {
        super(source, sink, edges, altEdges, edgesReversed, altEdgesReversed, nodesLinearised);
        this.id = id;
    }

    public XProcessId getId() {
        return id;
    }
}
