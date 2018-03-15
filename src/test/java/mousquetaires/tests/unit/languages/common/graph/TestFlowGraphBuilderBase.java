package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowGraphBuilder;
import mousquetaires.languages.common.graph.Node;


abstract class TestFlowGraphBuilderBase<T extends Node, G extends FlowGraph<T>>
        extends FlowGraphBuilder<T, G> {


    @Override
    public void setSource(T source) {
        super.setSource(source);
    }

    @Override
    public void setSink(T sink) {
        super.setSink(sink);
    }

    @Override
    public void addEdge(T from, T to) {
        super.addEdge(from, to);
    }

    @Override
    public void addAltEdge(T from, T to) {
        super.addAltEdge(from, to);
    }

    // TODO: HEAP POLLUTION HERE
    public void addPath(T from, T to, T... other) {
        addEitherEdge(from, to);
        T previous = to;
        for (T node : other) {
            addEitherEdge(previous, node);
            previous = node;
        }
    }

    private void addEitherEdge(T from, T to) {
        if (!hasEdgeFrom(from)) {
            super.addEdge(from, to);
        } else {
            super.addAltEdge(from, to);
        }
    }
}
