package mousquetaires.languages.common.graph.traverse;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.GraphNode;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;

import java.util.HashSet;


class CompositeTraverseActor<N extends GraphNode, G extends UnrolledFlowGraph<N>> implements
                                                                                  FlowGraphTraverseActor<N, G> {
    private final UnrolledFlowGraphBuilder<N, G> graphBuilder;
    private final ImmutableSet<FlowGraphTraverseActor<N, G>> actors;

    CompositeTraverseActor(UnrolledFlowGraphBuilder<N, G> graphBuilder) {
        this.graphBuilder = graphBuilder;
        // Add here new actors that gather information about graph during graph traverse
        ImmutableSet.Builder<FlowGraphTraverseActor<N, G>> actorsBuilder = new ImmutableSet.Builder<>();
        actorsBuilder.add(new UnrollingActor<>(graphBuilder));
        this.actors = actorsBuilder.build();
    }

    public G buildGraph() {
        return graphBuilder.build();
    }

    @Override
    public void onStart() {
        for (FlowGraphTraverseActor<N, G> actor : actors) {
            actor.onStart();
        }
    }

    @Override
    public void onNodePreVisit(N node) {
        for (FlowGraphTraverseActor<N, G> actor : actors) {
            actor.onNodePreVisit(node);
        }
    }

    @Override
    public void onEdgeVisit(boolean edgeKind, N from, N to) {
        for (FlowGraphTraverseActor<N, G> actor : actors) {
            actor.onEdgeVisit(edgeKind, from, to);
        }
    }

    @Override
    public void onNodePostVisit(N node) {
        for (FlowGraphTraverseActor<N, G> actor : actors) {
            actor.onNodePostVisit(node);
        }
    }

    @Override
    public void onBoundAchieved(N lastNode) {
        for (FlowGraphTraverseActor<N, G> actor : actors) {
            actor.onBoundAchieved(lastNode);
        }
    }

    @Override
    public void onFinish() {
        for (FlowGraphTraverseActor<N, G> actor : actors) {
            actor.onFinish();
        }
    }
}
