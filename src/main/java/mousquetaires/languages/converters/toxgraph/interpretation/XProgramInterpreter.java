package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.converters.toxgraph.hooks.HookManager;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.*;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XNopEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLocalMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XSharedMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.languages.syntax.xgraph.process.XProcessKind;
import mousquetaires.languages.syntax.xgraph.program.XCyclicProgram;
import mousquetaires.languages.syntax.xgraph.program.XCyclicProgramBuilder;
import mousquetaires.memorymodels.wmm.MemoryModel;
import mousquetaires.utils.patterns.BuilderBase;

import javax.annotation.Nullable;

import static mousquetaires.utils.StringUtils.wrap;


public class XProgramInterpreter extends BuilderBase<XCyclicProgram> implements XInterpreter {

    // TODO: publish methods also!
    private XCyclicProgramBuilder programBuilder;
    public final MemoryModel.Kind memoryModel;
    private final XMemoryManager memoryManager;
    private XInterpreter currentProcess;

    public XProgramInterpreter(XMemoryManager memoryManager, MemoryModel.Kind memoryModel) {
        this.memoryManager = memoryManager;
        this.programBuilder = new XCyclicProgramBuilder();
        this.memoryModel = memoryModel;
        // TODO: add prelude and postlude processes!..
    }

    @Override
    public XCyclicProgram build() {
        assert currentProcess == null : "process " + currentProcess.getProcessId() + " is not finished yet";
        return programBuilder.build(); //preludeBuilder.build(), process.build(), postludeBuilder.build());
    }

    @Override
    public void finishInterpretation() {
        throw new IllegalStateException("method is not used for this class");//todo: fix inheritance here
    }

    @Override
    public XCyclicProcess getResult() {
        throw new IllegalStateException("method is not used for this class");//todo: fix inheritance here
    }

    @Override
    public XProcessId getProcessId() {
        return currentProcess().getProcessId();
    }

    @Override
    public XLocation declareLocation(String name, Type type) {
        return currentProcess().declareLocation(name, type);
    }

    @Override
    public XRegister declareRegister(String name, Type type) {
        return currentProcess().declareRegister(name, type);
    }

    @Override
    public XRegister declareTempRegister(Type type) {
        return currentProcess().declareTempRegister(type);
    }

    @Override
    public XLvalueMemoryUnit declareUnresolvedUnit(String name, boolean isGlobal) {
        return currentProcess().declareUnresolvedUnit(name, isGlobal);
    }

    @Override
    public XLvalueMemoryUnit getDeclaredUnitOrNull(String name) {
        return currentProcess().getDeclaredUnitOrNull(name);
    }

    @Override
    public XRegister getDeclaredRegister(String name, XProcessId processId) {
        return currentProcess().getDeclaredRegister(name, processId);
    }

    public void startProcessDefinition(XProcessKind processKind, XProcessId processId) {
        resetState(processId);
        switch (processKind) {
            case Prelude:
                setCurrentProcess(new XPreludeInterpreter(XProcessId.PreludeProcessId, memoryManager));
                break;
            case ConcurrentProcess:
                setCurrentProcess(new XThreadInterpreter(processId, memoryManager, new HookManager(this)));
                break;
            case Postlude:
                setCurrentProcess(new XPostludeInterpreter(XProcessId.PostludeProcessId, memoryManager));
                break;
            default:
                throw new IllegalArgumentException(processKind.name());
        }
        currentProcess().emitEntryEvent();
    }

    public void finishProcessDefinition() {
        XInterpreter proc = currentProcess();
        proc.finishInterpretation();
        programBuilder.addProcess(proc.getResult());
        setCurrentProcess(null);
    }

    @Override
    public XLocalMemoryUnit tryConvertToLocalOrNull(XEntity expression) {
        return currentProcess().tryConvertToLocalOrNull(expression);
    }

    @Override
    public XRegister copyToLocalMemory(XSharedMemoryUnit shared) {
        return currentProcess().copyToLocalMemory(shared);
    }

    @Override
    public XComputationEvent tryEvaluateComputation(XEntity entity) {
        return currentProcess().tryEvaluateComputation(entity);
    }

    @Override
    public XEntryEvent emitEntryEvent() {
        return currentProcess().emitEntryEvent();
    }

    @Override
    public XExitEvent emitExitEvent() {
        return currentProcess().emitExitEvent();
    }

    @Override
    public XBarrierEvent emitBarrierEvent(XBarrierEvent.Kind kind) {
        return currentProcess().emitBarrierEvent(kind);
    }

    @Override
    public XJumpEvent emitJumpEvent() {
        return currentProcess().emitJumpEvent();
    }

    @Override
    public XNopEvent emitNopEvent() {
        return currentProcess().emitNopEvent();
    }

    @Override
    public XLocalMemoryEvent emitMemoryEvent(XLocalLvalueMemoryUnit destination, XLocalMemoryUnit source) {
        return currentProcess().emitMemoryEvent(destination, source);
    }

    @Override
    public XSharedMemoryEvent emitMemoryEvent(XLocalLvalueMemoryUnit destination, XSharedMemoryUnit source) {
        return currentProcess().emitMemoryEvent(destination, source);
    }

    @Override
    public XSharedMemoryEvent emitMemoryEvent(XSharedLvalueMemoryUnit destination, XLocalMemoryUnit source) {
        return currentProcess().emitMemoryEvent(destination, source);
    }

    // --

    //@Override
    //public XComputationEvent emitSimpleComputationEvent(XUnaryOperator operator, XLocalMemoryUnit operand) {
    //    return currentProcess().emitSimpleComputationEvent(operator, operand);
    //}
    //
    //@Override
    //public XComputationEvent emitSimpleComputationEvent(XBinaryOperator operator, XLocalMemoryUnit firstOperand, XLocalMemoryUnit secondOperand) {
    //    return currentProcess().emitSimpleComputationEvent(operator, firstOperand, secondOperand);
    //}

    @Override
    public XComputationEvent createComputationEvent(XUnaryOperator operator, XLocalMemoryUnit operand) {
        return currentProcess().createComputationEvent(operator, operand);
    }

    @Override
    public XComputationEvent createComputationEvent(XBinaryOperator operator,
                                                    XLocalMemoryUnit firstOperand,
                                                    XLocalMemoryUnit secondOperand) {
        return currentProcess().createComputationEvent(operator, firstOperand, secondOperand);
    }

    @Override
    public XAssertionEvent processAssertion(XBinaryComputationEvent assertion) {
        return currentProcess().processAssertion(assertion);
    }

    // --

    @Override
    public void startBlockDefinition(BlockKind blockKind) {
        currentProcess().startBlockDefinition(blockKind);
    }

    @Override
    public void startBlockConditionDefinition() {
        currentProcess().startBlockConditionDefinition();
    }

    @Override
    public void finishBlockConditionDefinition(XComputationEvent conditionEvent) {
        currentProcess().finishBlockConditionDefinition(conditionEvent);
    }

    @Override
    public void startBlockBranchDefinition(BranchKind branchKind) {
        currentProcess().startBlockBranchDefinition(branchKind);
    }

    @Override
    public void finishBlockBranchDefinition() {
        currentProcess().finishBlockBranchDefinition();
    }

    @Override
    public void finishNonlinearBlockDefinition() {
        currentProcess().finishNonlinearBlockDefinition();
    }

    @Override
    public void processJumpStatement(JumpKind kind) {
        currentProcess().processJumpStatement(kind);
    }

    @Override
    public XEntity processMethodCall(String methodName, @Nullable XMemoryUnit receiver, XMemoryUnit... arguments) {
        return currentProcess().processMethodCall(methodName, receiver, arguments);
    }

    private XInterpreter currentProcess() {
        if (currentProcess == null) {
            throw new IllegalStateException("Attempt to access the process without starting it");
        }
        return currentProcess;
    }

    private void setCurrentProcess(XInterpreter process) {
        assert currentProcess == null || process == null : currentProcess;
        currentProcess = process;
    }

    private void resetState(XProcessId nextProcessId) {
        if (currentProcess != null) {
            throw new IllegalStateException("Attempt to start new process definition " + wrap(nextProcessId) +
                                                    " while another process " + wrap(currentProcess.getProcessId()) +
                                                    " is still under construction");
        }
        memoryManager.reset(nextProcessId);
    }
}
