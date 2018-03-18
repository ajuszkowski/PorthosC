package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowGraphBuilderBase;
import mousquetaires.languages.common.graph.GraphNode;


abstract class TestFlowGraphBuilderBase<N extends GraphNode, G extends FlowGraph<N>>
        extends FlowGraphBuilderBase<N, G> {

    public TestFlowGraphBuilderBase(N source, N sink) {
            setSource(source);
            setSink(sink);
    }

    @Override
    public void setSource(N source) {
        super.setSource(source);
    }

    @Override
    public void setSink(N sink) {
        super.setSink(sink);
    }

    @Override
    public void addEdge(boolean edgeSign, N from, N to) {
        super.addEdge(edgeSign, from, to);
    }

    // TODO: HEAP POLLUTION HERE
    public void addPath(boolean edgeSign, N from, N to, N... other) {
        addEdge(edgeSign, from, to);
        N previous = to;
        for (N node : other) {
            addEdge(edgeSign, previous, node);
            previous = node;
        }
    }
}
