package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.common.XType;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.utils.exceptions.xgraph.XInterpretationError;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;


public abstract class XLudeInterpreterBase extends XInterpreterBase {

    private final Set<String> accessedLocalUnits;
    private final Set<String> accessedSharedUnits;

    public XLudeInterpreterBase(XProcessId processId, XMemoryManager memoryManager) {
        super(processId, memoryManager);
        this.accessedLocalUnits = new HashSet<>();
        this.accessedSharedUnits = new HashSet<>();
    }

    @Override
    protected void processNextEvent(XEvent nextEvent) {
        preProcessEvent(nextEvent);
        if (previousEvent != null) {
            graphBuilder.addEdge(true, previousEvent, nextEvent);
        }
        postProcessEvent(nextEvent);
    }

    // --

    @Override
    public XBarrierEvent emitBarrierEvent(XBarrierEvent.Kind kind) {
        throw new XInterpretationError(getIllegalOperationMessage());
    }

    @Override
    public XJumpEvent emitJumpEvent() {
        throw new XInterpretationError(getIllegalOperationMessage());
    }

    @Override
    public void startBlockDefinition(BlockKind blockKind) {
        throw new XInterpretationError(getIllegalOperationMessage());
    }

    @Override
    public void startBlockConditionDefinition() {
        throw new XInterpretationError(getIllegalOperationMessage());
    }

    @Override
    public void finishBlockConditionDefinition(XComputationEvent conditionEvent) {
        throw new XInterpretationError(getIllegalOperationMessage());
    }

    @Override
    public void startBlockBranchDefinition(BranchKind branchKind) {
        throw new XInterpretationError(getIllegalOperationMessage());
    }

    @Override
    public void finishBlockBranchDefinition() {
        throw new XInterpretationError(getIllegalOperationMessage());
    }

    @Override
    public void finishNonlinearBlockDefinition() {
        throw new XInterpretationError(getIllegalOperationMessage());
    }

    @Override
    public void processJumpStatement(JumpKind kind) {
        throw new XInterpretationError(getIllegalOperationMessage());
    }

    @Override
    public XEntity processMethodCall(String methodName, @Nullable XMemoryUnit receiver, XMemoryUnit... arguments) {
        throw new XInterpretationError(getIllegalOperationMessage());
    }
}
