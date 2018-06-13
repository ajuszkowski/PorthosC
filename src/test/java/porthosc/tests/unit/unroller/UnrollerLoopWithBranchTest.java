package porthosc.tests.unit.unroller;

import porthosc.tests.unit.languages.common.graph.*;
import org.junit.Test;

import static porthosc.tests.unit.languages.common.graph.IntFlowGraphBuilderHelper.addPath;
import static porthosc.tests.unit.languages.common.graph.IntGraphHelper.node;
import static porthosc.tests.unit.languages.common.graph.IntGraphHelper.r;


public class UnrollerLoopWithBranchTest extends UnrollerTestBase {

    @Test
    public void test_boundMeetsTwice() {
        final int bound = 8;
        // expected graph construction:
        IntNode source = node(0), sink = node(-1);
        UnrolledIntFlowGraphBuilder builder = new UnrolledIntFlowGraphBuilder(source, sink);

        //first forward path:
        builder.addEdge(true, source,  r(1, 1));
        addPath(builder, true, r(1, 1), r(2, 1), r(3, 1), r(4, 1), sink);
        //backtracking from (4,1):
        builder.addEdge(false, r(4, 1), r(1, 2));
        addPath(builder, true,  r(1, 2), r(2, 2), r(3, 2), r(4, 2), sink);
        //backtracking from (4,2):
        //builder.addEdge(false, r(4, 2), sink); //not need this edge, already has true-edge
        //backtracking from (3,2):
        builder.addEdge(false, r(3, 2), r(2, 3));
        addPath(builder, true,  r(2, 3), sink);
        //backtracking from (3,1):
        builder.addEdge(false, r(3, 1), r(2, 4));
        addPath(builder, true,  r(2, 4), r(3, 3), r(4, 3), sink);
        //backtracking from (4,3):
        builder.addEdge(false, r(4, 3), r(1, 3));
        addPath(builder, true,  r(1, 3), r(2, 5), sink);
        //backtracking from (3,3):
        builder.addEdge(false, r(3, 3), r(2, 6));
        addPath(builder, true,  r(2, 6), r(3, 4), r(4, 4), sink);
        //backtracking from (3,4):
        builder.addEdge(false, r(3, 4), r(2, 7));
        addPath(builder, true, r(2, 7), sink);

        UnrolledIntFlowGraph expectedUnrolled = builder.build();

        run(expectedUnrolled, getOriginalGraph(), bound);
    }

    @Override
    protected IntFlowGraph getOriginalGraph() {
        // length = 5
        IntNode source = node(0), sink = node(-1);
        IntFlowGraphBuilder builder = new IntFlowGraphBuilder(source, sink);
        addPath(builder, true, source, node(1), node(2), node(3), node(4), node(5), sink);
        builder.addEdge(false, node(5), node(1)); //loop back edge
        builder.addEdge(false, node(2), node(6)); //branching edge
        builder.addEdge(true, node(6), node(5)); //merge branch
        return builder.build();
    }
}
