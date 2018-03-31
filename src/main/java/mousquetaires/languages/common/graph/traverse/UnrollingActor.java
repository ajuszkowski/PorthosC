package mousquetaires.languages.common.graph.traverse;

import mousquetaires.languages.common.graph.FlowGraphNode;
import mousquetaires.languages.common.graph.FlowGraphNodeInfo;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;
import mousquetaires.utils.CollectionUtils;
import mousquetaires.utils.StringUtils;

import java.util.HashSet;
import java.util.Set;


class UnrollingActor<N extends FlowGraphNode, G extends UnrolledFlowGraph<N>>
        extends FlowGraphTraverseActor<N, G> {

    private Set<N> leaves;

    private boolean waitingForStartEvent = true;

    UnrollingActor(UnrolledFlowGraphBuilder<N, G> builder) {
        super(builder);
        this.leaves = new HashSet<>();
    }


    @Override
    public final void onEdgeVisit(boolean edgeKind, N from, N to) {
        preProcessEdge(from, to);
        builder.addEdge(edgeKind, from, to);
    }

    @Override
    public void onFinish() {
        setSink();
    }

    private void preProcessEdge(N from, N to) {
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

    private void setSink() {
        N computedSink = CollectionUtils.getSingleElement(leaves); //must be exactly 1 element in collection
        if (builder.getSink() == null) {
            builder.setSink(computedSink);
        }
        else {
            assert builder.getSink().equals(computedSink) : "sinks mismatch: old=" + builder.getSink() + ", new=" + computedSink;
        }
    }
}
