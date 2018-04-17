package mousquetaires.tests.unit.languages.converters.toxgraph.c11;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperator;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.memorymodels.wmm.MemoryModel;
import mousquetaires.tests.unit.UnitTestPaths;
import mousquetaires.tests.unit.languages.common.XTestMemoryManager;
import mousquetaires.tests.unit.languages.converters.toxgraph.C11ToXgraph_UnitTestBase;
import mousquetaires.tests.unit.languages.converters.toxgraph.XFlowGraphTestBuilder;
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
        XRegister registerX = memoryManager.declareRegister("x", Type.int32);
        XRegister registerY = memoryManager.declareRegister("y", Type.int32);
        XConstant const1 = XConstant.create(1, Type.int32);
        XConstant const2 = XConstant.create(2, Type.int32);
        XConstant const3 = XConstant.create(3, Type.int32);
        XConstant const4 = XConstant.create(4, Type.int32);

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
