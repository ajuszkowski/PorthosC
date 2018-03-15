package mousquetaires.languages.common.graph.traverse;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.Node;

import java.util.Set;


class CompositeFlowGraphTraverseActor<T extends Node> implements FlowGraphTraverseActor<T> {

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
    public void onVisitEdge(boolean condition, T from, T to) {
        for (FlowGraphTraverseActor<T> actor : actors) {
            actor.onVisitEdge(condition, from, to);
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
