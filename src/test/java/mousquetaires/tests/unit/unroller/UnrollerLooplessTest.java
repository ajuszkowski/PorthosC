package mousquetaires.tests.unit.unroller;

import mousquetaires.tests.unit.languages.common.graph.IntGraphNode;
import mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraph;
import mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraphBuilder;
import org.junit.Test;

import static mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraphBuilder.node;
import static mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraphBuilder.r;


public class UnrollerLooplessTest extends UnrollerTestBase {

    @Test
    public void test_boundIsEnough() {
        final int bound = 6;
        // expected graph construction:
        IntGraphNode source = node(0), sink = node(-1);
        IntTestFlowGraphBuilder builder = new IntTestFlowGraphBuilder(source, sink);
        builder.addPath(true, source, r(1, 1), r(2, 1), r(3, 1), r(4, 1), sink);
        builder.addPath(false, r(2, 1), r(6, 1), r(4, 1));
        builder.addPath(false, r(1, 1), r(5, 1), r(4, 1));
        IntTestFlowGraph expectedUnrolled = builder.build();

        run(expectedUnrolled, getNonUnrolledGraph(), bound);
    }

    @Test
    public void test_boundCutsCode() {
        final int bound = 3;

        // expected graph construction:
        IntGraphNode source = node(0), sink = node(-1);
        IntTestFlowGraphBuilder builder = new IntTestFlowGraphBuilder(source, sink);
        builder.addPath(true, source, r(1, 1), r(2, 1), r(3, 1), sink);
        builder.addPath(false, r(2, 1), r(6, 1), sink);
        builder.addPath(false, r(1, 1), r(5, 1), r(4, 1), sink);
        IntTestFlowGraph expectedUnrolled = builder.build();

        run(expectedUnrolled, getNonUnrolledGraph(), bound);
    }

    @Override
    protected IntTestFlowGraph getNonUnrolledGraph() {
        // length = 4
        IntGraphNode source = node(0), sink = node(-1);
        IntTestFlowGraphBuilder builder = new IntTestFlowGraphBuilder(source, sink);
        builder.addPath(true, source, node(1), node(2), node(3), node(4), sink);
        builder.addPath(false, node(2), node(6), node(4));
        builder.addPath(false, node(1), node(5), node(4));
        return builder.build();
    }
}
