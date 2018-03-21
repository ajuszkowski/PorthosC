package mousquetaires.languages.common.graph.traverse;

import mousquetaires.languages.common.graph.GraphNode;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;
import mousquetaires.utils.CollectionUtils;
import mousquetaires.utils.StringUtils;
import mousquetaires.utils.patterns.BuilderBase;

import java.util.HashSet;
import java.util.Set;


class UnrollingActor<T extends GraphNode, G extends UnrolledFlowGraph<T>>
        extends BuilderBase<G>
        implements FlowGraphTraverseActor<T, G> {

    private final UnrolledFlowGraphBuilder<T, G> builder;
    private Set<T> leaves;

    private boolean waitingForStartEvent = true;

    UnrollingActor(UnrolledFlowGraphBuilder<T, G> builder) {
        this.builder = builder;
        this.leaves = new HashSet<>();
    }

    @Override
    public G build() {
        return builder.build();
    }

    @Override
    public final void onEdgeVisit(boolean edgeKind, T from, T to) {
        preProcessEdge(from, to);
        builder.addEdge(edgeKind, from, to);
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
