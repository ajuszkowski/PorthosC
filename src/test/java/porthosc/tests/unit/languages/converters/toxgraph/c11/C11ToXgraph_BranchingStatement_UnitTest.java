package porthosc.tests.unit.languages.converters.toxgraph.c11;

import porthosc.languages.common.XType;
import porthosc.languages.syntax.xgraph.events.computation.XBinaryOperator;
import porthosc.languages.syntax.xgraph.events.computation.XComputationEvent;
import porthosc.languages.syntax.xgraph.events.memory.XMemoryEvent;
import porthosc.languages.syntax.xgraph.memories.XConstant;
import porthosc.languages.syntax.xgraph.memories.XRegister;
import porthosc.languages.syntax.xgraph.process.XCyclicProcess;
import porthosc.languages.syntax.xgraph.process.XProcessId;
import porthosc.memorymodels.wmm.MemoryModel;
import porthosc.tests.unit.UnitTestPaths;
import porthosc.tests.unit.languages.common.XTestMemoryManager;
import porthosc.tests.unit.languages.converters.toxgraph.C11ToXgraph_UnitTestBase;
import porthosc.tests.unit.languages.converters.toxgraph.XFlowGraphTestBuilder;
import org.junit.Test;


public class C11ToXgraph_BranchingStatement_UnitTest extends C11ToXgraph_UnitTestBase {

    @Override
    protected MemoryModel.Kind memoryModel() {
        return MemoryModel.Kind.TSO;//temporary
    }

    @Test
    public void test() {
        XProcessId processId = new XProcessId("P0");//TODO: process id
        XFlowGraphTestBuilder builder = new XFlowGraphTestBuilder(processId);
        XTestMemoryManager memoryManager = new XTestMemoryManager(processId);
        XRegister registerX = memoryManager.declareRegister("x", XType.int32);
        XRegister registerY = memoryManager.declareRegister("y", XType.int32);
        XConstant const1 = XConstant.create(1, XType.int32);
        XConstant const2 = XConstant.create(2, XType.int32);
        XConstant const3 = XConstant.create(3, XType.int32);
        XConstant const4 = XConstant.create(4, XType.int32);

        XComputationEvent conditionXequals1 = builder.createComputationEvent(XBinaryOperator.CompareEquals, registerX, const1);
        XComputationEvent conditionXgreater2 = builder.createComputationEvent(XBinaryOperator.CompareGreater, registerX, const2);
        XMemoryEvent assignY2 = builder.createAssignmentEvent(registerY, const2);
        XMemoryEvent assignXY = builder.createAssignmentEvent(registerX, registerY);
        XMemoryEvent assignY3 = builder.createAssignmentEvent(registerY, const3);
        XMemoryEvent assignX4 = builder.createAssignmentEvent(registerX, const4);

        builder.processFirstEvent(conditionXequals1);
        builder.processBranchingEvent(conditionXequals1, assignY2, conditionXgreater2);
        builder.processNextEvent(assignY2, assignXY);
        builder.processBranchingEvent(conditionXgreater2, assignY3, assignX4);
        builder.processNextEvent(assignXY, assignX4);
        builder.processNextEvent(assignY3, assignX4);
        builder.processLastEvents(assignX4);

        XCyclicProcess process = builder.build();

        run(UnitTestPaths.c11StatementsDirectory + "branchingStatement.c",
            getIterator(process));
    }
}
