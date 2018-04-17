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
import org.antlr.v4.codegen.model.Loop;

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

    default void finalise() {
        // do nothing by default
    }

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

    XComputationEvent emitComputationEvent(XUnaryOperator operator, XLocalMemoryUnit operand);

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

    // TODO: signature instead of just name
    // TODO: arguments: write all shared to registers and set up control-flow binding
    XEntity processMethodCall(String methodName, @Nullable XMemoryUnit receiver, XMemoryUnit... arguments);
}
