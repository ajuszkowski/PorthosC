package mousquetaires.languages.common.graph.traverse;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowGraphBuilder;
import mousquetaires.languages.common.graph.Node;
import mousquetaires.utils.StringUtils;
import mousquetaires.utils.patterns.Builder;


public class FlowGraphUnrollingActor<T extends Node, G extends FlowGraph<T>>
        extends Builder<G>
        implements FlowGraphTraverseActor<T> {

    private final FlowGraphBuilder<T, G> builder;

    private boolean waitingForStartEvent = true;

    public FlowGraphUnrollingActor(FlowGraphBuilder<T, G> builder) {
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
        preProcessEdge(from, to);
        builder.addEdge(from, to);
    }

    @Override
    public final void onVisitAltEdge(T from, T to) {
        preProcessEdge(from, to);
        builder.addAltEdge(from, to);
    }

    @Override
    public final void onBoundAchieved(T lastNode, T sinkNode) {
        if (builder.getSink() == null) {
            builder.setSink(sinkNode);
        }
        else {
            assert builder.getSink().equals(sinkNode) : "sinks mismatch: old=" + builder.getSink() + ", new=" + sinkNode;
        }
        builder.addEdge(lastNode, builder.getSink());
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
    }
}
