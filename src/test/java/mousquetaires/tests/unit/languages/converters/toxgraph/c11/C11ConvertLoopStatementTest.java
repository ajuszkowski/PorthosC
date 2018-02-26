package mousquetaires.tests.unit.languages.converters.toxgraph.c11;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XOperator;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryManager;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;
import mousquetaires.tests.unit.UnitTestPaths;
import mousquetaires.tests.unit.languages.converters.toxgraph.AbstractXgraphUnitTest;
import mousquetaires.tests.unit.languages.converters.toxgraph.XProcessTestBuilder;
import org.junit.Test;


public class C11ConvertLoopStatementTest extends AbstractXgraphUnitTest {

    @Test
    public void test_loopStatement() {
        XProcessTestBuilder builder = new XProcessTestBuilder("?");//TODO: process id
        XMemoryManager memoryManager = new XMemoryManager(ProgramLanguage.C11, null);
        XRegister registerX = memoryManager.getLocalMemoryUnit("x");
        XRegister registerY = memoryManager.getLocalMemoryUnit("y");

        XComputationEvent conditionXequals0 = builder.createComputationEvent(XOperator.CompareEquals,  registerX, memoryManager.getConstant(0));
        XComputationEvent conditionXgreater2 = builder.createComputationEvent(XOperator.CompareGreater, registerX, memoryManager.getConstant(2));
        XComputationEvent conditionXgreater3 = builder.createComputationEvent(XOperator.CompareGreater, registerX, memoryManager.getConstant(3));
        XComputationEvent conditionXgreater4 = builder.createComputationEvent(XOperator.CompareGreater, registerX, memoryManager.getConstant(4));
        XComputationEvent conditionYequals5 = builder.createComputationEvent(XOperator.CompareGreater, registerY, memoryManager.getConstant(5));
        XComputationEvent conditionXequals7 = builder.createComputationEvent(XOperator.CompareEquals, registerX, memoryManager.getConstant(7));
        XComputationEvent conditionConst10 = builder.createComputationEvent(memoryManager.getConstant(10));

        XMemoryEvent assignY1 = builder.createAssignmentEvent(registerY, memoryManager.getConstant(1));
        XMemoryEvent assignXY = builder.createAssignmentEvent(registerX, registerY);
        XMemoryEvent assignYX = builder.createAssignmentEvent(registerY, registerX);
        XMemoryEvent assignY6 = builder.createAssignmentEvent(registerY, memoryManager.getConstant(6));
        XMemoryEvent assignX8 = builder.createAssignmentEvent(registerX, memoryManager.getConstant(8));
        XMemoryEvent assignY9 = builder.createAssignmentEvent(registerY, memoryManager.getConstant(9));
        XMemoryEvent assignX11 = builder.createAssignmentEvent(registerX, memoryManager.getConstant(11));

        XJumpEvent jump1 = builder.createJumpEvent();
        XJumpEvent jump2 = builder.createJumpEvent();
        XJumpEvent jump3 = builder.createJumpEvent();

        builder.processFirstEvent(conditionXequals0);
        //first 'while'
        builder.processBranchingEvent(conditionXequals0, assignY1, conditionXgreater3);
        builder.processNextEvent(assignY1, conditionXgreater2);
        builder.processBranchingEvent(conditionXgreater2, jump1, assignXY);
        builder.processNextEvent(jump1, conditionXgreater3);
        builder.processNextEvent(assignXY, conditionXequals0);
        //second 'while'
        builder.processBranchingEvent(conditionXgreater3, assignYX, conditionConst10);
        builder.processNextEvent(assignYX, conditionXgreater4);
        builder.processBranchingEvent(conditionXgreater4, conditionYequals5, assignY9);
        builder.processBranchingEvent(conditionYequals5, jump2, assignY6);
        builder.processNextEvent(jump2, conditionXgreater4);
        builder.processNextEvent(assignY6, conditionXequals7);
        builder.processBranchingEvent(conditionXequals7, jump3, assignX8);
        builder.processNextEvent(jump3, assignY9);
        builder.processNextEvent(assignY9, conditionXgreater3);
        builder.processNextEvent(assignX8, conditionXgreater4);
        //third 'while'
        builder.processBranchingEvent(conditionConst10, conditionConst10, assignX11);
        builder.processLastEvent(assignX11);

        XProcess process = builder.build();

        run( UnitTestPaths.c11StatementsDirectory + "loopStatement.c",
                getIterator(process));
    }
}
