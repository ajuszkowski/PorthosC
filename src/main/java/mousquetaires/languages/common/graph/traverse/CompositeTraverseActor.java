package mousquetaires.languages.common.graph.traverse;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.FlowGraphNode;
import mousquetaires.languages.common.graph.FlowGraphNodeInfo;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;


class CompositeTraverseActor<N extends FlowGraphNode, G extends UnrolledFlowGraph<N>>
        extends FlowGraphTraverseActor<N, G> {

    private final ImmutableSet<FlowGraphTraverseActor<N, G>> actors;

    CompositeTraverseActor(UnrolledFlowGraphBuilder<N, G> builder) {
        super(builder);
        // Add here new actors that gather information about graph during graph traverse
        ImmutableSet.Builder<FlowGraphTraverseActor<N, G>> actorsBuilder = new ImmutableSet.Builder<>();
        actorsBuilder.add(new UnrollingActor<>(builder));
        actorsBuilder.add(new DfsLinearisationActor<>(builder));
        this.actors = actorsBuilder.build();
    }

    public G buildGraph() {
        return builder.build();
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
    public void onLastNodeVisit(N lastNode) {
        for (FlowGraphTraverseActor<N, G> actor : actors) {
            actor.onLastNodeVisit(lastNode);
        }
    }

    @Override
    public void onFinish() {
        for (FlowGraphTraverseActor<N, G> actor : actors) {
            actor.onFinish();
        }
    }
}
