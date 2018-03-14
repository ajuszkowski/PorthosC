package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.FlowGraphBuilder;


public abstract class TestFlowGraphBuilder extends FlowGraphBuilder<TestNode> {

    public TestFlowGraphBuilder(String processId) {
        super(processId);
    }

    //public final void addPath(int... nodes) {
    //    assert nodes.length > 2;
    //    int from = nodes[0], to;
    //    for (int i = 1; i < nodes.length; i++) {
    //        to = nodes[i];
    //        addEdge(from, to);
    //        from = to;
    //    }
    //}

}
