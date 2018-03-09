package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.utils.StringUtils;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.Set;


public class XFlowGraph implements XEntity, FlowGraph<XEvent> {

    private final String processId;
    private final XEntryEvent entry;
    private final XExitEvent exit;
    private final ImmutableMap<XEvent, XEvent> edges;//next, goto jumps
    private final ImmutableMap<XEvent, XEvent> alternativeEdges; //if(false)
    private ImmutableMap<XEvent, ImmutableSet<XEvent>> edgesReversed;
    public final boolean isUnrolled;

    public XFlowGraph(String processId,
               XEntryEvent entry,
               XExitEvent exit,
               ImmutableMap<XEvent, XEvent> edges,
               ImmutableMap<XEvent, XEvent> alternativeEdges,
               boolean isUnrolled) {
        this.processId = processId;
        this.entry = entry;
        this.exit = exit;
        this.edges = edges;
        this.alternativeEdges = alternativeEdges;
        this.isUnrolled = isUnrolled;
    }

    public String processId() {
        return processId;
    }

    @Override
    public XEvent source() {
        return entry;
    }

    @Override
    public XEvent sink() {
        return exit;
    }

    @Override
    public XEvent child(XEvent node) {
        if (node instanceof XExitEvent) {
            throw new IllegalArgumentException("No outgoing edges from exit event");
        }
        if (edges.containsKey(node)) {
            return edges.get(node);
        }
        return getChild(edges, node);
    }

    @Override
    public XEvent alternativeChild(XEvent node) {
        if (!(node instanceof XComputationEvent)) {
            throw new IllegalArgumentException("Only computation events can be branching nodes, received: " +
                    node.getClass().getSimpleName());
        }
        return getChild(alternativeEdges, node);
    }

    @Override
    public Set<XEvent> parents(XEvent node) {
        throw new NotImplementedException();
    }

    @Override
    public Set<XEvent> nodes() {
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        return "XProcess{" + "processId='" + processId + '\'' +
                ", edges=" + StringUtils.jsonSerialize(edges) +
                ", alternativeEdges=" + StringUtils.jsonSerialize(alternativeEdges) +
                '}';
    }

    @Override
    public boolean hasAlternativeChild(XEvent node) {
        return node instanceof XComputationEvent && alternativeEdges.containsKey(node);
    }


    public ImmutableMap<XEvent, XEvent> edges() {
        return edges;
    }

    @Override
    public ImmutableMap<XEvent, XEvent> alternativeEdges() {
        return alternativeEdges;
    }

    private XEvent getChild(ImmutableMap<? extends XEvent, XEvent> jumps, XEvent condition) {
        if (!jumps.containsKey(condition)) {
            throw new IllegalArgumentException("Cannot find outgoing edge from node " + condition);
        }
        return jumps.get(condition);
    }

}
