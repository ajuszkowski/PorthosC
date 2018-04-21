package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.exceptions.xgraph.XInterpretationError;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;


public class XLudeInterpreter extends XInterpreterBase {

    private final Set<String> accessedLocalUnits;
    private final Set<String> accessedSharedUnits;

    private XEvent previousEvent;

    public XLudeInterpreter(XProcessId processId, XMemoryManager memoryManager) {
        super(processId, memoryManager);
        this.accessedLocalUnits = new HashSet<>();
        this.accessedSharedUnits = new HashSet<>();
    }

    @Override
    protected void processNextEvent(XEvent nextEvent) {
        assert nextEvent != null;
        if (previousEvent != null) {
            graphBuilder.addEdge(true, previousEvent, nextEvent);
        }
        previousEvent = nextEvent;
    }

    @Override
    public void processAssertion(XLocalMemoryUnit assertion) {
        throw new NotImplementedException();
    }

    // --

    @Override
    public XLocation declareLocation(String name, Type type) {
        accessedLocalUnits.add(name);
        return super.declareLocation(name, type);
    }

    @Override
    public XRegister declareRegister(String name, Type type) {
        accessedSharedUnits.add(name);
        return super.declareRegister(name, type);
    }

    @Override
    public XLvalueMemoryUnit declareUnresolvedUnit(String name, boolean isGlobal) {
        if (isGlobal) {
            accessedSharedUnits.add(name);
        }
        else {
            accessedLocalUnits.add(name);
        }
        return super.declareUnresolvedUnit(name, isGlobal);
    }

    @Override
    public XLvalueMemoryUnit getDeclaredUnitOrNull(String name) {
        XLvalueMemoryUnit result = super.getDeclaredUnitOrNull(name);
        if (result != null) {
            if (result instanceof XLocalMemoryUnit) {
                accessedLocalUnits.add(name);
            }
            else if (result instanceof XSharedMemoryUnit) {
                accessedSharedUnits.add(name);
            }
            else {
                throw new IllegalStateException("memory unit may be either local or shared, found: " + result.getClass().getSimpleName());
            }
        }
        return result;
    }

    @Override
    public XRegister getDeclaredRegister(String name, XProcessId processId) {
        return super.getDeclaredRegister(name, processId);
    }

    // --



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
    public void finishBlockConditionDefinition() {
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
