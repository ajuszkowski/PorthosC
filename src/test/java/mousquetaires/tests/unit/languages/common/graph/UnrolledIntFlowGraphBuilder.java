package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;


public class UnrolledIntFlowGraphBuilder extends UnrolledFlowGraphBuilder<IntFlowGraphNode, UnrolledIntFlowGraph> {

    public UnrolledIntFlowGraphBuilder(IntFlowGraphNode source, IntFlowGraphNode sink) {
        setSource(source);
        setSink(sink);
    }

    @Override
    public UnrolledIntFlowGraph build() {
        finishBuilding();
        return new UnrolledIntFlowGraph(getSource(), getSink(),
                                        buildEdges(true),
                                        buildEdges(false),
                                        buildReversedEdges(true),
                                        buildReversedEdges(false),
                                        buildNodesLinearised());
    }

}
