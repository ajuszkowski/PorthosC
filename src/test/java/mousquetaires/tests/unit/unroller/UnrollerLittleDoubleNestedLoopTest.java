package mousquetaires.tests.unit.unroller;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventRef;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;
import mousquetaires.languages.syntax.xgraph.events.memory.XMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryManager;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.tests.unit.languages.converters.toxgraph.XFlowGraphTestBuilder;
import org.junit.Test;


public class UnrollerLittleDoubleNestedLoopTest extends UnrollerTestBase {

    @Test
    public void test() {
        XFlowGraphTestBuilder actualBuilder = new XFlowGraphTestBuilder("?"); //TODO: process id
        XMemoryManager memoryManager = new XMemoryManager(ProgramLanguage.C11, null);
        XRegister registerX = memoryManager.getLocalMemoryUnit("x");
        XConstant const1 = memoryManager.getConstant(1);
        XConstant const2 = memoryManager.getConstant(2);
        XConstant const3 = memoryManager.getConstant(3);
        XConstant const4 = memoryManager.getConstant(4);
        XConstant const5 = memoryManager.getConstant(5);

        XComputationEvent conditionXequals3 = actualBuilder.createComputationEvent(XZOperator.CompareEquals,  registerX, const3);
        XComputationEvent conditionXequals4 = actualBuilder.createComputationEvent(XZOperator.CompareEquals,  registerX, const4);
        XMemoryEvent assignX1 = actualBuilder.createAssignmentEvent(registerX, const1);
        XMemoryEvent assignX2 = actualBuilder.createAssignmentEvent(registerX, const2);
        XMemoryEvent assignX5 = actualBuilder.createAssignmentEvent(registerX, const5);

        actualBuilder.processFirstEvent(assignX1);
        actualBuilder.processNextEvent(assignX1, assignX2, conditionXequals3);
        actualBuilder.processBranchingEvent(conditionXequals3, conditionXequals4, assignX2);
        actualBuilder.processBranchingEvent(conditionXequals4, assignX1, assignX5);

        actualBuilder.processLastEvents(assignX5);

        XFlowGraph actualNonUnrolled = actualBuilder.build();

        XFlowGraphTestBuilder expectedBuilder5 = new XFlowGraphTestBuilder("?");
        // TODO: create separately constants, registers, ...
        expectedBuilder5.processFirstEvent(ref(assignX1, 1));
        expectedBuilder5.processNextEvent(ref(assignX1, 1), ref(assignX2, 2), ref(conditionXequals3, 3));
        expectedBuilder5.processBranchingEvent(ref(conditionXequals3, 3), ref(conditionXequals4, 4), ref(assignX2, 4));
        expectedBuilder5.processBranchingEvent(ref(conditionXequals4, 4), ref(assignX1, 5), ref(assignX5, 5));
        expectedBuilder5.processNextEvent(ref(assignX2, 4), ref(conditionXequals3, 5));

        expectedBuilder5.processLastEvents(ref(assignX1, 5), ref(conditionXequals3, 5), ref(assignX5, 5));
        XFlowGraph expectedUnrolled5 = expectedBuilder5.build();

        run(expectedUnrolled5, actualNonUnrolled, 6);
    }

    private XEventRef ref(XEvent event, int id) {
        return new XEventRef(event, id);
    }
    //private FlowGraph<TestNode> getCyclicGraph() {
    //    ActualTestFlowGraphBuilder builderActual = new ActualTestFlowGraphBuilder();
    //    // example from the meeting:
    //    builderActual.addPath(1, 2, 3, 4);
    //    builderActual.addAlternativeEdge(3, 2);
    //    builderActual.addAlternativeEdge(4, 1);
    //    builderActual.finishBuilding(100);
    //    return builderActual.build();
    //}
}
