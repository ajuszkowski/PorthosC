package mousquetaires.languages.common.graph.traverse;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.GraphNode;

import java.util.Set;


class CompositeDfsActor<T extends GraphNode, G extends FlowGraph<T>> implements FlowGraphDfsActor<T, G> {

    private final ImmutableSet<FlowGraphDfsActor<T, G>> actors;

    CompositeDfsActor(Set<FlowGraphDfsActor<T, G>> actors) {
        this.actors = ImmutableSet.copyOf(actors);
    }

    public ImmutableSet<FlowGraphDfsActor<T, G>> getActors() {
        return actors;
    }

    @Override
    public void onStart() {
        for (FlowGraphDfsActor<T, G> actor : actors) {
            actor.onStart();
        }
    }

    @Override
    public void onNodePreVisit(T node) {
        for (FlowGraphDfsActor<T, G> actor : actors) {
            actor.onNodePreVisit(node);
        }
    }

    @Override
    public void onEdgeVisit(boolean edgeKind, T from, T to) {
        for (FlowGraphDfsActor<T, G> actor : actors) {
            actor.onEdgeVisit(edgeKind, from, to);
        }
    }

    @Override
    public void onNodePostVisit(T node) {
        for (FlowGraphDfsActor<T, G> actor : actors) {
            actor.onNodePostVisit(node);
        }
    }

    @Override
    public void onBoundAchieved(T lastNode) {
        for (FlowGraphDfsActor<T, G> actor : actors) {
            actor.onBoundAchieved(lastNode);
        }
    }

    @Override
    public void onFinish() {
        for (FlowGraphDfsActor<T, G> actor : actors) {
            actor.onFinish();
        }
    }
}
