package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowGraphBuilder;
import mousquetaires.languages.common.graph.GraphNode;


abstract class TestFlowGraphBuilderBase<T extends GraphNode, G extends FlowGraph<T>>
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
    public void addEdge(boolean edgeSign, T from, T to) {
        super.addEdge(edgeSign, from, to);
    }

    // TODO: HEAP POLLUTION HERE
    public void addPath(boolean edgeSign, T from, T to, T... other) {
        addEdge(edgeSign, from, to);
        T previous = to;
        for (T node : other) {
            addEdge(edgeSign, previous, node);
            previous = node;
        }
    }
}
