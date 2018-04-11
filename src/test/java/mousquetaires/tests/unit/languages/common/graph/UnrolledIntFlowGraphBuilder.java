package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;


public class UnrolledIntFlowGraphBuilder extends UnrolledFlowGraphBuilder<IntNode, UnrolledIntFlowGraph> {

    public UnrolledIntFlowGraphBuilder(IntNode source, IntNode sink) {
        setSource(source);
        setSink(sink);
    }

    @Override
    public UnrolledIntFlowGraph build() {
        finishBuilding();
        return new UnrolledIntFlowGraph(getSource(),
                                        getSink(),
                                        buildEdges(true),
                                        buildEdges(false),
                                        buildReversedEdges(true),
                                        buildReversedEdges(false),
                                        buildNodesLinearised(),
                                        buildCondLevelMap());
    }

    @Override
    public IntNode createNodeRef(IntNode node, int newRefId) {
        return new IntNode(node.getId(), newRefId);
    }
}
