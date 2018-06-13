package porthosc.tests.unit.unroller;

import porthosc.tests.unit.languages.common.graph.*;
import org.junit.Test;

import static porthosc.tests.unit.languages.common.graph.IntFlowGraphBuilderHelper.addPath;
import static porthosc.tests.unit.languages.common.graph.IntGraphHelper.node;
import static porthosc.tests.unit.languages.common.graph.IntGraphHelper.r;


public class UnrollerLooplessTest extends UnrollerTestBase {

    @Test
    public void test_boundIsEnough() {
        final int bound = 6;
        // expected graph construction:
        IntNode source = node(0), sink = node(-1);
        UnrolledIntFlowGraphBuilder builder = new UnrolledIntFlowGraphBuilder(source, sink);
        addPath(builder, true, source, r(1, 1), r(2, 1), r(3, 1), r(4, 1), sink);
        addPath(builder, false, r(2, 1), r(6, 1), r(4, 1));
        addPath(builder, false, r(1, 1), r(5, 1), r(4, 1));
        UnrolledIntFlowGraph expectedUnrolled = builder.build();

        run(expectedUnrolled, getOriginalGraph(), bound);
    }

    @Test
    public void test_boundCutsCode() {
        final int bound = 3;

        // expected graph construction:
        IntNode source = node(0), sink = node(-1);
        UnrolledIntFlowGraphBuilder builder = new UnrolledIntFlowGraphBuilder(source, sink);
        addPath(builder, true, source, r(1, 1), r(2, 1), r(3, 1), sink);
        addPath(builder, false, r(2, 1), r(6, 1), sink);
        addPath(builder, false, r(1, 1), r(5, 1), r(4, 1), sink);
        UnrolledIntFlowGraph expectedUnrolled = builder.build();

        run(expectedUnrolled, getOriginalGraph(), bound);
    }

    @Override
    protected IntFlowGraph getOriginalGraph() {
        // length = 4
        IntNode source = node(0), sink = node(-1);
        IntFlowGraphBuilder builder = new IntFlowGraphBuilder(source, sink);
        addPath(builder, true, source, node(1), node(2), node(3), node(4), sink);
        addPath(builder, false, node(2), node(6), node(4));
        addPath(builder, false, node(1), node(5), node(4));
        return builder.build();
    }
}
