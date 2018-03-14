package mousquetaires.languages.common.graph.traverse;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowGraphBuilder;
import mousquetaires.languages.common.graph.GraphNode;
import mousquetaires.languages.syntax.xgraph.events.XEventRef;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.utils.CollectionUtils;
import mousquetaires.utils.patterns.Builder;

import java.util.HashSet;
import java.util.Set;


public class FlowGraphUnroller<T extends GraphNode, G extends FlowGraph<T>>
        extends Builder<G>
        implements FlowGraphTraverseActor<T> {

    private final FlowGraphBuilder<T, G> builder;

    private boolean waitingForStartEvent = true;
    private Set<T> visitedNodes = new HashSet<>();
    private Set<T> leaves = new HashSet<>();

    public FlowGraphUnroller(FlowGraphBuilder<T, G> builder) {
        this.builder = builder;
    }

    @Override
    public G build() {
        return builder.build();
    }

    @Override
    public void onStart() {
        // do nothing
    }

    @Override
    public final void onVisitEdge(T from, T to) {
        processEdge(from, to);
        builder.addEdge(from, to);
    }

    @Override
    public final void onVisitAltEdge(T from, T to) {
        processEdge(from, to);
        builder.addAlternativeEdge(from, to);
    }

    @Override
    public final void onBoundAchieved(T lastNode) {
        leaves.remove(lastNode);
        if (builder.getSink() == null) {
            builder.setSink(CollectionUtils.getSingleElement(leaves));
        }
        builder.addEdge(lastNode, builder.getSink());
    }

    @Override
    public void onFinish() {
        builder.markAsUnrolled();
    }

    private void processEdge(T from, T to) {
        assert (from instanceof XEventRef) || (from instanceof XEntryEvent) : from.getClass().getSimpleName();
        assert (to instanceof XEventRef)   || (to instanceof XExitEvent)    : from.getClass().getSimpleName();
        if (waitingForStartEvent) {
            builder.setSource(from);
            waitingForStartEvent = false;
        }

        leaves.remove(from);
        if (!visitedNodes.contains(to)) {
            leaves.add(to);
        }

        visitedNodes.add(from);
        visitedNodes.add(to);
    }
}
