package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryManager;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.languages.syntax.xgraph.process.interpretation.XFlowGraphInterpretationBuilder;
import mousquetaires.utils.patterns.Builder;


public class XProgramInterpretationBuilder extends Builder<XProgram> {

    public XFlowGraphInterpretationBuilder currentProcess;
    private final ImmutableList.Builder<XFlowGraph> processes;
    // TODO: publish methods also!
    private final XMemoryManager memoryManager;

    public XProgramInterpretationBuilder(XMemoryManager memoryManager) {
        this.memoryManager = memoryManager;
        this.processes = new ImmutableList.Builder<>();
        //this.preludeBuilder = new XPreProcessBuilder(memoryManager);
        //this.postludeBuilder = new XPostProcessBuilder(memoryManager);
    }

    @Override
    public XProgram build() {
        markFinished();
        if (currentProcess != null) {
            processes.add(currentProcess.build());
        }
        return new XProgram(processes.build()); //preludeBuilder.build(), process.build(), postludeBuilder.build());
    }

    //private final XPreProcessBuilder preludeBuilder;
    //private final XPostProcessBuilder postludeBuilder;
    //private boolean startedProcessesDefinition = false;
    //public void beginPreludeDefinition() {
    //    if (currentProcess != null || startedProcessesDefinition) {
    //        throw new IllegalStateException("Attempt to start prelude definition while another process '"
    //                + currentProcess.buildProcessId() + "'; is being constructed");
    //    }
    //    currentProcess = preludeBuilder;
    //}
    //
    //public void endPreludeDefinition() {
    //    endProcessDefinition();
    //}
    //public void beginPostludeDefinition() {
    //    if (currentProcess != null) {
    //        throw new IllegalStateException("Attempt to start postlude definition while another process '"
    //                + currentProcess.buildProcessId() + "'; is being constructed");
    //    }
    //    if (!startedProcessesDefinition) {
    //        throw new IllegalStateException("Attempt to start postlude definition before definition of the process");
    //    }
    //    currentProcess = postludeBuilder;
    //}
    //
    //public void endPostludeDefinition() {
    //    endProcessDefinition();
    //}


    public void startProcessDefinition(String processId) {
        if (currentProcess != null) {
            throw new IllegalStateException("Attempt to start new process '" + processId +
                    "' definition while another process '" + currentProcess.buildProcessId() +
                    "'; is being constructed");
        }
        currentProcess = new XFlowGraphInterpretationBuilder(processId, memoryManager);
    }

    public void finishProcessDefinition() {
        if (currentProcess == null) {
            throw new IllegalStateException("Attempt to end process definition without starting it");
        }
        processes.add(currentProcess.build());
        currentProcess = null;
        //if (currentProcess instanceof XProcessBuilder) {
        //    XParallelProcessBuilder currentParBuilder = (XParallelProcessBuilder) currentProcess;
        //    process.add(currentParBuilder.build());
        //    currentProcess = null;
        //}
        // for pre & post do nothing, because current is a reference to it
    }

    //public XBinaryOperationEvent emitComputationEvent(XOperator operator, XMemoryUnit leftOperand, XMemoryUnit rightOperand) {
    //    XRegister left = leftOperand instanceof XRegister
    //            ? (XRegister) leftOperand
    //            : copyToLocalMemoryIfNecessary(leftOperand);
    //    XRegister right = rightOperand instanceof XRegister
    //            ? (XRegister) rightOperand
    //            : copyToLocalMemoryIfNecessary(rightOperand);
    //    return currentProcess.emitComputationEvent(operator, left, right);
    //}


    //public XMemoryEventBase emitMemoryEvent(XMemoryUnit source) {
    //    return emitMemoryEvent(memoryManager.newLocalMemoryUnit(), source);
    //}
    //
    //public XMemoryEventBase emitMemoryEvent(XMemoryUnit destination, XMemoryUnit source) {
    //    XRegister destinationLocal = destination instanceof XRegister
    //            ? (XRegister) destination
    //            : null;
    //    XRegister sourceLocal = source instanceof XRegister
    //            ? (XRegister) source
    //            : null;
    //    XRegister destinationShared = destination instanceof XRegister
    //            ? (XRegister) destination
    //            : null;
    //    XRegister sourceShared = source instanceof XRegister
    //            ? (XRegister) source
    //            : null;
    //
    //    if (destinationLocal != null) {
    //        if (sourceLocal != null) {
    //            return currentProcess.emitMemoryEvent(destinationLocal, sourceLocal);
    //        }
    //        if (sourceShared != null) {
    //            return currentProcess.emitMemoryEvent(destinationLocal, sourceShared);
    //        }
    //    }
    //    if (destinationShared != null) {
    //        if (sourceLocal != null) {
    //            return currentProcess.emitMemoryEvent(destinationShared, sourceLocal);
    //        }
    //        if (sourceShared != null) {
    //            XRegister tempLocal = copyToLocalMemoryIfNecessary(sourceShared);
    //            return currentProcess.emitMemoryEvent(destinationShared, tempLocal);
    //        }
    //    }
    //    throw new IllegalStateException();
    //}
    //
    //public XControlFlowEvent emitControlFlowEvent() {
    //    throw new NotImplementedException();
    //}
    //
    //public XBarrierEvent emitBarrierEvent() {
    //    throw new NotImplementedException();
    //}

}
