package mousquetaires.languages.common.graph.traverse;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.GraphNode;

import java.util.Set;


class CompositeFlowGraphTraverseActor<T extends GraphNode> implements FlowGraphTraverseActor<T> {

    private final ImmutableSet<FlowGraphTraverseActor<T>> actors;

    CompositeFlowGraphTraverseActor(FlowGraphTraverseActor<T>... actors) {
        this(Set.of(actors));
    }

    CompositeFlowGraphTraverseActor(Set<FlowGraphTraverseActor<T>> actors) {
        this.actors = ImmutableSet.copyOf(actors);
    }

    public ImmutableSet<FlowGraphTraverseActor<T>> getActors() {
        return actors;
    }

    @Override
    public void onStart() {
        for (FlowGraphTraverseActor<T> actor : actors) {
            actor.onStart();
        }
    }

    @Override
    public void onVisitEdge(T from, T to) {
        for (FlowGraphTraverseActor<T> actor : actors) {
            actor.onVisitEdge(from, to);
        }
    }

    @Override
    public void onVisitAltEdge(T from, T to) {
        for (FlowGraphTraverseActor<T> actor : actors) {
            actor.onVisitAltEdge(from, to);
        }
    }

    @Override
    public void onBoundAchieved(T lastNode) {
        for (FlowGraphTraverseActor<T> actor : actors) {
            actor.onBoundAchieved(lastNode);
        }
    }

    @Override
    public void onFinish() {
        for (FlowGraphTraverseActor<T> actor : actors) {
            actor.onFinish();
        }
    }
}