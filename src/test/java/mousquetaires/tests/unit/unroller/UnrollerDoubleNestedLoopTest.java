package mousquetaires.tests.unit.unroller;

import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.tests.unit.languages.converters.toxgraph.XFlowGraphTestBuilder;
import org.junit.Test;

import static mousquetaires.tests.unit.unroller.FlowGraphHelper.ref;


public class UnrollerDoubleNestedLoopTest extends UnrollerTestBase {

    @Test
    public void test_unrollingBound6() {
        final int unrollingBound = 6; //TODO: pass as parameter?

        // EXPECTED GRAPH CONSTRUCTION
        XFlowGraph actualNonUnrolled = getNonUnrolledGraph();
        XFlowGraphTestBuilder expectedBuilder = createTestGraphBuilder();
        expectedBuilder.processFirstEvent(ref(assignX1, 1));
        expectedBuilder.processNextEvent(ref(assignX1, 1), ref(assignX2, 1), ref(conditionXequals3, 1));
        expectedBuilder.processBranchingEvent(ref(conditionXequals3, 1), ref(conditionXequals4, 1), ref(assignX2, 2));
        expectedBuilder.processBranchingEvent(ref(conditionXequals4, 1), ref(assignX1, 2), ref(assignX5, 1));
        expectedBuilder.processNextEvent(ref(assignX2, 2), ref(conditionXequals3, 2));
        expectedBuilder.processLastEvents(ref(assignX1, 2), ref(conditionXequals3, 2), ref(assignX5, 1));
        XFlowGraph expectedUnrolled = expectedBuilder.build();

        run(expectedUnrolled, actualNonUnrolled, unrollingBound);
    }




    private XFlowGraph getNonUnrolledGraph() {
        // ACTUAL GRAPH CONSTRUCTION
        XFlowGraphTestBuilder actualBuilder = createTestGraphBuilder(); //TODO: process id

        actualBuilder.processFirstEvent(assignX1);
        actualBuilder.processNextEvent(assignX1, assignX2, conditionXequals3);
        actualBuilder.processBranchingEvent(conditionXequals3, conditionXequals4, assignX2);
        actualBuilder.processBranchingEvent(conditionXequals4, assignX1, assignX5);
        actualBuilder.processLastEvents(assignX5);

        return actualBuilder.build();
    }
}
