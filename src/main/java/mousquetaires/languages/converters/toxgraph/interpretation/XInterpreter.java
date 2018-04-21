package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperator;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryOperator;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XNopEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLocalMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XSharedMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;

import javax.annotation.Nullable;


public interface XInterpreter {

    enum BlockKind {
        Sequential,
        Branching,
        Loop,
        ;
    }

    enum BranchKind {
        Then,
        Else,
        ;
    }

    enum JumpKind {
        Break,
        Continue,
        ;
    }

    XCyclicProcess getResult();

    XProcessId getProcessId();

    void finishInterpretation();

    XLocation declareLocation(String name, Type type);
    XRegister declareRegister(String name, Type type);
    XRegister newTempRegister(Type type);
    XLvalueMemoryUnit declareUnresolvedUnit(String name, boolean isGlobal);
    XLvalueMemoryUnit getDeclaredUnitOrNull(String name);
    XRegister getDeclaredRegister(String name, XProcessId processId);

    XLocalMemoryUnit tryConvertToLocalOrNull(XEntity expression);

    XLocalLvalueMemoryUnit tryConvertToLocalLvalueOrNull(XEntity expression);

    XRegister copyToLocalMemory(XSharedMemoryUnit shared);

    XConstant getConstant(Object value, Type type);

    XComputationEvent evaluateMemoryUnit(XMemoryUnit memoryUnit);

    XEntryEvent emitEntryEvent();

    XExitEvent emitExitEvent();

    XBarrierEvent emitBarrierEvent(XBarrierEvent.Kind kind);

    XJumpEvent emitJumpEvent();

    XNopEvent emitNopEvent();

    XComputationEvent createComputationEvent(XUnaryOperator operator, XLocalMemoryUnit operand);

    XComputationEvent emitComputationEvent(XUnaryOperator operator, XLocalMemoryUnit operand);

    XComputationEvent createComputationEvent(XBinaryOperator operator, XLocalMemoryUnit firstOperand, XLocalMemoryUnit secondOperand);

    XComputationEvent emitComputationEvent(XBinaryOperator operator, XLocalMemoryUnit firstOperand, XLocalMemoryUnit secondOperand);

    XLocalMemoryEvent emitMemoryEvent(XLocalLvalueMemoryUnit destination, XLocalMemoryUnit source);

    XSharedMemoryEvent emitMemoryEvent(XLocalLvalueMemoryUnit destination, XSharedMemoryUnit source);

    XSharedMemoryEvent emitMemoryEvent(XSharedLvalueMemoryUnit destination, XLocalMemoryUnit source);

    void startBlockDefinition(BlockKind blockKind);

    void startBlockConditionDefinition();

    void finishBlockConditionDefinition();

    void startBlockBranchDefinition(BranchKind branchKind);

    void finishBlockBranchDefinition();

    void finishNonlinearBlockDefinition();

    void processJumpStatement(JumpKind kind);

    void processAssertion(XLocalMemoryUnit assertion);

    // TODO: signature instead of just name
    // TODO: arguments: write all shared to registers and set up control-flow binding
    XEntity processMethodCall(String methodName, @Nullable XMemoryUnit receiver, XMemoryUnit... arguments);
}
