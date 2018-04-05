package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.syntax.xgraph.XProcessLocalElement;
import mousquetaires.languages.syntax.xgraph.events.XEvent;

import java.util.HashSet;
import java.util.Set;


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

    //TODO: old-code method, to be replaced
    public Set<XEvent> getAllEvents() {
        //false-edges must be already in the set
        HashSet<XEvent> result = new HashSet<>(getEdges(true).keySet());
        result.add(sink());
        return result;
    }

    public XProcessId getId() {
        return id;
    }
}
