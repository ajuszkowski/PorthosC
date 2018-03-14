package mousquetaires.tests.unit.unroller;

import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.tests.unit.languages.converters.toxgraph.XFlowGraphTestBuilder;
import org.junit.Test;

import static mousquetaires.tests.unit.unroller.FlowGraphHelper.ref;


public class UnrollerLooplessTest extends UnrollerTestBase {

    @Test
    public void test() {
        int unrollingBound = 6; //TODO: pass as parameter?
        // ALMOST as in branchingStatement.c

        XFlowGraph actualNonUnrolled = getNonUnrolledGraph();

        // EXPECTED GRAPH CONSTRUCTION
        XFlowGraphTestBuilder expectedBuilder5 = createTestGraphBuilder();
        expectedBuilder5.processFirstEvent(ref(conditionXequals1, 1));
        expectedBuilder5.processBranchingEvent(ref(conditionXequals1, 1), ref(assignY2, 1), ref(conditionXgreater2, 1));
        expectedBuilder5.processNextEvent(ref(assignY2, 1), ref(conditionXequals2, 1));
        expectedBuilder5.processBranchingEvent(ref(conditionXequals2, 1), ref(assignXY, 1), ref(assignX4, 1));
        expectedBuilder5.processNextEvent(ref(assignXY, 1), ref(assignX4, 1));
        expectedBuilder5.processBranchingEvent(ref(conditionXgreater2, 1), ref(assignY3, 1), ref(assignX4, 1));
        expectedBuilder5.processNextEvent(ref(assignY3, 1), ref(assignX4, 1));
        expectedBuilder5.processLastEvents(ref(assignX4, 1));

        // TODO: create separately constants, registers, ...
        XFlowGraph expectedUnrolled6 = expectedBuilder5.build();

        run(expectedUnrolled6, actualNonUnrolled, unrollingBound);
    }

    private XFlowGraph getNonUnrolledGraph() {
        // ACTUAL GRAPH CONSTRUCTION
        XFlowGraphTestBuilder actualBuilder = createTestGraphBuilder();

        actualBuilder.processFirstEvent(conditionXequals1);
        actualBuilder.processBranchingEvent(conditionXequals1, assignY2, conditionXgreater2);
        actualBuilder.processNextEvent(assignY2, conditionXequals2);
        actualBuilder.processBranchingEvent(conditionXequals2, assignXY, assignX4); //THIS IS UNEXISTING IN branchingStatement.c EVENT
        actualBuilder.processNextEvent(assignXY, assignX4);
        actualBuilder.processBranchingEvent(conditionXgreater2, assignY3, assignX4);
        actualBuilder.processNextEvent(assignY3, assignX4);
        actualBuilder.processLastEvents(assignX4);

        return actualBuilder.build();
    }
}
