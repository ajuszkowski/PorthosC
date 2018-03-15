package mousquetaires.tests.unit.unroller;

import mousquetaires.tests.unit.languages.common.graph.IntNode;
import mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraph;
import mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraphBuilder;
import org.junit.Test;

import static mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraphBuilder.node;
import static mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraphBuilder.r;


public class UnrollerDoubleNestedLoopTest extends UnrollerTestBase {

    @Test
    public void test_boundMeetsOnce() {
        final int bound = 6;
        IntTestFlowGraph actualNonUnrolled = getNonUnrolledGraph();

        // expected graph construction:
        IntNode source = node(0), sink = node(-1);
        IntTestFlowGraphBuilder builder = new IntTestFlowGraphBuilder(source, sink);
        builder.addPath(source,  r(1, 1), r(2, 1), r(3, 1), r(4, 1), r(1, 2), sink);
        builder.addPath(r(3, 1), r(2, 2), r(3, 2), sink);
        builder.addPath(r(4, 1), r(5, 1), sink);
        IntTestFlowGraph expectedUnrolled = builder.build();

        run(expectedUnrolled, actualNonUnrolled, bound);
    }

    @Override
    protected IntTestFlowGraph getNonUnrolledGraph() {
        // length = 5
        IntNode source = node(0), sink = node(-1);
        IntTestFlowGraphBuilder builder = new IntTestFlowGraphBuilder(source, sink);
        builder.addPath(source, node(1), node(2), node(3), node(4), sink);
        builder.addAltEdge(node(3), node(2));
        builder.addAltEdge(node(4), node(1));
        return builder.build();
    }
}
