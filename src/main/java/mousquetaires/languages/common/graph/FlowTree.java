package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XMemoryEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;


public abstract class FlowTree<
        N extends FlowGraphNode,
        G extends FlowGraph<N>> {

    private final ImmutableList<G> graphs;
    private final Map<Predicate<N>, ImmutableSet<N>> nodeQueriesCache;

    private final Map<Predicate<XEvent>, ImmutableSet<XEvent>> memoisedEvents;

    public FlowTree(ImmutableList<G> graphs) {
        this.graphs = graphs;
        this.nodeQueriesCache = new HashMap<>();
        this.memoisedEvents = new HashMap<>();
    }

    public ImmutableSet<N> getNodes(Predicate<N> filter) {
        if (nodeQueriesCache.containsKey(filter)) {
            return nodeQueriesCache.get(filter);
        }
        ImmutableSet.Builder<N> builder = new ImmutableSet.Builder<>();
        for (G process : getGraphs()) {
            for (N node : process.getAllNodes()) {
                if (filter.test(node)) {
                    builder.add(node);
                }
            }
        }
        ImmutableSet<N> result = builder.build();
        nodeQueriesCache.put(filter, result);
        return result;
    }

    protected ImmutableList<G> getGraphs() {
        return graphs;
    }
}
