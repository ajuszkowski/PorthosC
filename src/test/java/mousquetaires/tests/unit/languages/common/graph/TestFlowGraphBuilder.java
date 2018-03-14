package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowGraphBuilder;
import mousquetaires.languages.common.graph.GraphNode;
import mousquetaires.utils.exceptions.NotSupportedException;

import java.util.HashSet;


public abstract class TestFlowGraphBuilder<T extends GraphNode, G extends FlowGraph<T>> extends FlowGraphBuilder<T, G> {

    private HashSet<T> leaves = new HashSet<>();

    protected TestFlowGraphBuilder(String processId, T source, T sink) {
        super(processId);
        super.setSource(source);
        super.setSink(sink);
    }

    @Override
    public void setSource(T source) {
        throw new NotSupportedException();
    }

    @Override
    public void setSink(T sink) {
        throw new NotSupportedException();
    }

    @Override
    public void addEdge(T from, T to) {
        preprocessNewEdge(from, to);
        super.addEdge(from, to);
    }

    @Override
    public void addAlternativeEdge(T from, T to) {
        preprocessNewEdge(from, to);
        super.addAlternativeEdge(from, to);
    }

    @Override
    public void finishBuilding() {
        super.finishBuilding();
        for (T leaf : leaves) {
            addEdge(leaf, getSink());
        }
        leaves = null;
    }

    private void preprocessNewEdge(T from, T to) {
        if (leaves.contains(from)) {
            leaves.remove(from);
        }
        if (!edges.containsKey(to)) {
            leaves.add(to);
        }
    }
}
