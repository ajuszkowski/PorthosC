package mousquetaires.tests.unit.unroller;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;
import mousquetaires.languages.syntax.xgraph.events.memory.XMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryManager;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.languages.transformers.xgraph.XFlowGraphUnroller;
import mousquetaires.tests.unit.AbstractUnitTest;
import mousquetaires.tests.unit.FlowGraphComparer;
import mousquetaires.tests.unit.languages.converters.toxgraph.XFlowGraphTestBuilder;
import mousquetaires.utils.CollectionUtils;
import org.junit.Assert;


public class UnrollerTestBase extends AbstractUnitTest<XFlowGraph> {

    protected final XFlowGraphTestBuilder createTestGraphBuilder() {
        return new XFlowGraphTestBuilder("P0");
    }

    // TODO: since most unit-tests operate only with single process, remember its id+...

    protected final XMemoryManager memoryManager = new XMemoryManager(ProgramLanguage.C11, null);
    protected final XRegister registerX = memoryManager.getLocalMemoryUnit("x");
    protected final XRegister registerY = memoryManager.getLocalMemoryUnit("y");
    protected final XConstant const1 = memoryManager.getConstant(1);
    protected final XConstant const2 = memoryManager.getConstant(2);
    protected final XConstant const3 = memoryManager.getConstant(3);
    protected final XConstant const4 = memoryManager.getConstant(4);
    protected final XConstant const5 = memoryManager.getConstant(5);

    protected final XComputationEvent conditionXequals1 = getFakeTestBuilder().createComputationEvent(XZOperator.CompareEquals,  registerX, const1);
    protected final XComputationEvent conditionXequals2 = getFakeTestBuilder().createComputationEvent(XZOperator.CompareEquals,  registerX, const2);
    protected final XComputationEvent conditionXequals3 = getFakeTestBuilder().createComputationEvent(XZOperator.CompareEquals,  registerX, const3);
    protected final XComputationEvent conditionXequals4 = getFakeTestBuilder().createComputationEvent(XZOperator.CompareEquals,  registerX, const4);
    protected final XComputationEvent conditionXgreater2= getFakeTestBuilder().createComputationEvent(XZOperator.CompareGreater,  registerX, const2);
    protected final XMemoryEvent assignX1 = getFakeTestBuilder().createAssignmentEvent(registerX, const1);
    protected final XMemoryEvent assignX2 = getFakeTestBuilder().createAssignmentEvent(registerX, const2);
    protected final XMemoryEvent assignX3 = getFakeTestBuilder().createAssignmentEvent(registerX, const3);
    protected final XMemoryEvent assignX4 = getFakeTestBuilder().createAssignmentEvent(registerX, const4);
    protected final XMemoryEvent assignX5 = getFakeTestBuilder().createAssignmentEvent(registerX, const5);
    protected final XMemoryEvent assignXY = getFakeTestBuilder().createAssignmentEvent(registerX, registerY);
    protected final XMemoryEvent assignY2 = getFakeTestBuilder().createAssignmentEvent(registerY, const2);
    protected final XMemoryEvent assignY3 = getFakeTestBuilder().createAssignmentEvent(registerY, const3);


    protected void run(XFlowGraph expectedUnrolled, XFlowGraph actualNonUnrolled, int bound) {
        XFlowGraphUnroller unroller = new XFlowGraphUnroller(actualNonUnrolled, bound);
        unroller.doUnroll();
        XFlowGraph actualUnrolled = unroller.getUnrolledGraph();
        compareResults(CollectionUtils.createIteratorFrom(expectedUnrolled),
                       CollectionUtils.createIteratorFrom(actualUnrolled));
    }

    //protected FlowGraph<TestNode> unrollGraph(FlowGraph<TestNode> graph, int bound) {
    //    ActualTestFlowGraphBuilder builder = new ActualTestFlowGraphBuilder();
    //    FlowGraphUnroller<TestNode> unroller = new FlowGraphUnroller<>(graph) {
    //        @Override
    //        protected TestNode createNodeRef(TestNode node, int refId) {
    //            return builder.nodeToRef(node);
    //        }
    //    };
    //    unroller.unrollIntoBuilder(bound, builder);
    //    return builder.build();
    //}
    //@Override
    //protected void assertObjectsEqual(FlowGraph<TestNode> expected, FlowGraph<TestNode> actual) {
    //    FlowGraphComparer.assertGraphsEqual(expected, actual);
    //}

    @Override
    protected void assertObjectsEqual(XFlowGraph expected, XFlowGraph actual) {
        Assert.assertEquals("process ID mismatch", expected.processId(), actual.processId());
        FlowGraphComparer.assertGraphsEqual(expected, actual);
    }

    private XFlowGraphTestBuilder fakeTestBuilder;
    private XFlowGraphTestBuilder getFakeTestBuilder() {
        return fakeTestBuilder == null
                ? (fakeTestBuilder = createTestGraphBuilder())
                : fakeTestBuilder;
    }
}
