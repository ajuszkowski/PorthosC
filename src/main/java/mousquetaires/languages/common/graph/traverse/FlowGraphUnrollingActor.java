package mousquetaires.languages.common.graph.traverse;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowGraphBuilder;
import mousquetaires.languages.common.graph.GraphNode;
import mousquetaires.utils.CollectionUtils;
import mousquetaires.utils.StringUtils;
import mousquetaires.utils.patterns.Builder;

import java.util.HashSet;
import java.util.Set;


public class FlowGraphUnrollingActor<T extends GraphNode, G extends FlowGraph<T>>
        extends Builder<G>
        implements FlowGraphTraverseActor<T> {

    private final FlowGraphBuilder<T, G> builder;
    private Set<T> leaves;

    private boolean waitingForStartEvent = true;

    public FlowGraphUnrollingActor(FlowGraphBuilder<T, G> builder) {
        this.builder = builder;
        this.leaves = new HashSet<>();
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
    public final void onVisitEdge(boolean condition, T from, T to) {
        preProcessEdge(from, to);
        builder.addEdge(condition, from, to);
    }

    @Override
    public final void onBoundAchieved(T lastNode) {
        T computedSink = CollectionUtils.getSingleElement(leaves); //must be exactly 1 element in collection
        if (builder.getSink() == null) {
            builder.setSink(computedSink);
        }
        else {
            assert builder.getSink().equals(computedSink) : "sinks mismatch: old=" + builder.getSink() + ", new=" + computedSink;
        }
        //builder.addEdge(true, lastNode, builder.getSink()); //already added
    }

    @Override
    public void onFinish() {
        builder.markAsUnrolled();
    }

    private void preProcessEdge(T from, T to) {
        if (waitingForStartEvent) {
            if (builder.getSource() == null) {
                builder.setSource(from);
            }
            else {
                assert builder.getSource().equals(from) : "computed source node " + StringUtils.wrap(from) +
                        " is not the same as pre-set node " + StringUtils.wrap(builder.getSource());
            }
            waitingForStartEvent = false;
        }

        leaves.remove(from);
        if (!builder.hasEdgesFrom(to)) {
            leaves.add(to);
        }
    }
}
