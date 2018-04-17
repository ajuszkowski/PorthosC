package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
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
import mousquetaires.utils.exceptions.xgraph.XInterpretationError;

import javax.annotation.Nullable;


class XPreludeInterpreter extends XInterpreterBase {

    private XEvent previousEvent;

    XPreludeInterpreter(XProcessId processId, XMemoryManager memoryManager) {
        super(processId, memoryManager);
    }

    @Override
    protected void processNextEvent(XEvent nextEvent) {
        assert nextEvent != null;
        if (previousEvent != null) {
            graphBuilder.addEdge(true, previousEvent, nextEvent);
        }
        previousEvent = nextEvent;
    }

    // --

    @Override
    public XBarrierEvent emitBarrierEvent(XBarrierEvent.Kind kind) {
        throw new XInterpretationError(getNotSupportedMessage());
    }

    @Override
    public XJumpEvent emitJumpEvent() {
        throw new XInterpretationError(getNotSupportedMessage());
    }

    @Override
    public void startBlockDefinition(BlockKind blockKind) {
        throw new XInterpretationError(getNotSupportedMessage());
    }

    @Override
    public void startBlockConditionDefinition() {
        throw new XInterpretationError(getNotSupportedMessage());
    }

    @Override
    public void finishBlockConditionDefinition() {
        throw new XInterpretationError(getNotSupportedMessage());
    }

    @Override
    public void startBlockBranchDefinition(BranchKind branchKind) {
        throw new XInterpretationError(getNotSupportedMessage());
    }

    @Override
    public void finishBlockBranchDefinition() {
        throw new XInterpretationError(getNotSupportedMessage());
    }

    @Override
    public void finishNonlinearBlockDefinition() {
        throw new XInterpretationError(getNotSupportedMessage());
    }

    @Override
    public void processJumpStatement(JumpKind kind) {
        throw new XInterpretationError(getNotSupportedMessage());
    }

    @Override
    public XEntity processMethodCall(String methodName, @Nullable XMemoryUnit receiver, XMemoryUnit... arguments) {
        throw new XInterpretationError(getNotSupportedMessage());
    }

    private String getNotSupportedMessage() {
        return "Not supported for prelude statement";
    }
}
