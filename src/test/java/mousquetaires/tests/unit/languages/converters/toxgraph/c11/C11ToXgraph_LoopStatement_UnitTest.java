package mousquetaires.tests.unit.languages.converters.toxgraph.c11;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperator;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.memorymodels.wmm.MemoryModelKind;
import mousquetaires.tests.unit.UnitTestPaths;
import mousquetaires.tests.unit.languages.common.XTestMemoryManager;
import mousquetaires.tests.unit.languages.converters.toxgraph.C11ToXgraph_UnitTestBase;
import mousquetaires.tests.unit.languages.converters.toxgraph.XFlowGraphTestBuilder;
import org.junit.Test;


public class C11ToXgraph_LoopStatement_UnitTest extends C11ToXgraph_UnitTestBase {

    @Override
    protected MemoryModelKind memoryModel() {
        return MemoryModelKind.TSO;//temporary
    }

    @Test
    public void test() {
        MemoryModelKind memoryModel = MemoryModelKind.TSO;

        XProcessId processId = new XProcessId("P0");//TODO: process id
        XFlowGraphTestBuilder builder = new XFlowGraphTestBuilder(processId);
        XTestMemoryManager memoryManager = new XTestMemoryManager(processId);
        XRegister registerX = memoryManager.declareRegister("x", Type.int32);
        XRegister registerY = memoryManager.declareRegister("y", Type.int32);

        XComputationEvent conditionXequals0 = builder.createComputationEvent(XBinaryOperator.CompareEquals, registerX,   XConstant.create(0, Type.int32));
        XComputationEvent conditionXgreater2 = builder.createComputationEvent(XBinaryOperator.CompareGreater, registerX, XConstant.create(2, Type.int32));
        XComputationEvent conditionXgreater3 = builder.createComputationEvent(XBinaryOperator.CompareGreater, registerX, XConstant.create(3, Type.int32));
        XComputationEvent conditionXgreater4 = builder.createComputationEvent(XBinaryOperator.CompareGreater, registerX, XConstant.create(4, Type.int32));
        XComputationEvent conditionYequals5 = builder.createComputationEvent(XBinaryOperator.CompareGreater, registerY,  XConstant.create(5, Type.int32));
        XComputationEvent conditionXequals7 = builder.createComputationEvent(XBinaryOperator.CompareEquals, registerX,   XConstant.create(7, Type.int32));
        XComputationEvent conditionConst10 = builder.createComputationEvent(XConstant.create(10, Type.int32));

        XMemoryEvent assignY1 = builder.createAssignmentEvent(registerY, XConstant.create(1, Type.int32));
        XMemoryEvent assignXY = builder.createAssignmentEvent(registerX, registerY);
        XMemoryEvent assignYX = builder.createAssignmentEvent(registerY, registerX);
        XMemoryEvent assignY6 = builder.createAssignmentEvent(registerY, XConstant.create(6, Type.int32));
        XMemoryEvent assignX8 = builder.createAssignmentEvent(registerX, XConstant.create(8, Type.int32));
        XMemoryEvent assignY9 = builder.createAssignmentEvent(registerY, XConstant.create(9, Type.int32));
        XMemoryEvent assignX11 = builder.createAssignmentEvent(registerX, XConstant.create(11, Type.int32));

        builder.processFirstEvent(conditionXequals0);
        //first 'while'
        builder.processBranchingEvent(conditionXequals0, assignY1, conditionXgreater3);
        builder.processNextEvent(assignY1, conditionXgreater2);
        builder.processBranchingEvent(conditionXgreater2, conditionXgreater3, assignXY);
        builder.processNextEvent(assignXY, conditionXequals0);
        //second 'while'
        builder.processBranchingEvent(conditionXgreater3, assignYX, conditionConst10);
        builder.processNextEvent(assignYX, conditionXgreater4);
        builder.processBranchingEvent(conditionXgreater4, conditionYequals5, assignY9);
        builder.processBranchingEvent(conditionYequals5, conditionXgreater4, assignY6);
        builder.processNextEvent(assignY6, conditionXequals7);
        builder.processBranchingEvent(conditionXequals7, assignY9, assignX8);
        builder.processNextEvent(assignY9, conditionXgreater3);
        builder.processNextEvent(assignX8, conditionXgreater4);
        //third 'while'
        builder.processBranchingEvent(conditionConst10, conditionConst10, assignX11);
        builder.processLastEvents(assignX11);

        XProcess process = builder.build();

        run(UnitTestPaths.c11StatementsDirectory + "loopStatement.c",
            getIterator(process));
    }
}
