package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.converters.toxgraph.hooks.HookManager;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.XProgramBuilder;
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
import mousquetaires.languages.syntax.xgraph.process.XProcessKind;
import mousquetaires.memorymodels.wmm.MemoryModel;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.patterns.BuilderBase;

import javax.annotation.Nullable;

import static mousquetaires.utils.StringUtils.wrap;


public class XProgramInterpreter extends BuilderBase<XProgram> implements XInterpreter {

    // TODO: publish methods also!
    private XProgramBuilder programBuilder;
    public final MemoryModel.Kind memoryModel;
    public final XMemoryManager memoryManager;
    public XInterpreter currentProcess;

    public XProgramInterpreter(XMemoryManager memoryManager, MemoryModel.Kind memoryModel) {
        this.memoryManager = memoryManager;
        this.programBuilder = new XProgramBuilder();
        this.memoryModel = memoryModel;
        // TODO: add prelude and postlude processes!..
    }

    @Override
    public XProgram build() {
        markFinished();
        assert currentProcess == null : "process " + currentProcess.getProcessId() + " is not finished yet";
        return programBuilder.build(); //preludeBuilder.build(), process.build(), postludeBuilder.build());
    }

    @Override
    public XCyclicProcess getResult() {
        throw new IllegalStateException("method is not used for this class");//todo: fix inheritance here
    }

    @Override
    public XProcessId getProcessId() {
        throw new IllegalStateException("method is not used for this class");//todo: fix inheritance here
    }

    public void startProcessDefinition(XProcessKind processKind, XProcessId processId) {
        resetState(processId);
        switch (processKind) {
            case Prelude:
                setCurrentProcess(new XPreludeInterpreter(processId, memoryManager));
                break;
            case ConcurrentProcess:
                setCurrentProcess(new XProcessInterpreter(processId, memoryManager, new HookManager(this)));
                currentProcess().emitEntryEvent();
                break;
            case Postlude:
                throw new NotImplementedException();
                //break;
            default:
                throw new IllegalArgumentException(processKind.name());
        }
    }

    public void finishProcessDefinition() {
        XInterpreter proc = currentProcess();
        proc.finalise();
        programBuilder.addProcess(proc.getResult());
        setCurrentProcess(null);
    }



    @Override
    public XLocalMemoryUnit tryConvertToLocalOrNull(XEntity expression) {
        return currentProcess().tryConvertToLocalOrNull(expression);
    }

    @Override
    public XLocalLvalueMemoryUnit tryConvertToLocalLvalueOrNull(XEntity expression) {
        return currentProcess().tryConvertToLocalLvalueOrNull(expression);
    }

    @Override
    public XRegister copyToLocalMemory(XSharedMemoryUnit shared) {
        return currentProcess().copyToLocalMemory(shared);
    }

    @Override
    public XConstant getConstant(Object value, Type type) {
        return currentProcess().getConstant(value, type);
    }

    @Override
    public XComputationEvent evaluateMemoryUnit(XMemoryUnit memoryUnit) {
        return currentProcess().evaluateMemoryUnit(memoryUnit);
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
    public XComputationEvent emitComputationEvent(XUnaryOperator operator, XLocalMemoryUnit operand) {
        return currentProcess().emitComputationEvent(operator, operand);
    }

    @Override
    public XComputationEvent emitComputationEvent(XBinaryOperator operator,
                                                  XLocalMemoryUnit firstOperand,
                                                  XLocalMemoryUnit secondOperand) {
        return currentProcess().emitComputationEvent(operator, firstOperand, secondOperand);
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

    @Override
    public void startBlockDefinition(BlockKind blockKind) {
        currentProcess().startBlockDefinition(blockKind);
    }

    @Override
    public void startBlockConditionDefinition() {
        currentProcess().startBlockConditionDefinition();
    }

    @Override
    public void finishBlockConditionDefinition() {
        currentProcess().finishBlockConditionDefinition();
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
        assert currentProcess == null : currentProcess;
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
