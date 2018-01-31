package mousquetaires.languages.syntax.xrepr;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XMemoryManager;
import mousquetaires.languages.syntax.xrepr.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.xrepr.processes.XProcess;
import mousquetaires.languages.syntax.xrepr.processes.XProcessBuilder;
import mousquetaires.utils.patterns.Builder;


public class XProgramBuilder extends Builder<XProgram> {

    public XProcessBuilder currentProcess;
    private final ImmutableList.Builder<XProcess> processes;
    // TODO: publish methods also!
    public final XMemoryManager memoryManager;

    public XProgramBuilder(XMemoryManager memoryManager) {
        this.memoryManager = memoryManager;
        this.processes = new ImmutableList.Builder<>();
        //this.preludeBuilder = new XPreProcessBuilder(memoryManager);
        //this.postludeBuilder = new XPostProcessBuilder(memoryManager);
    }

    @Override
    public XProgram build() {
        finishBuilding();
        if (currentProcess != null) {
            processes.add(currentProcess.build());
        }
        return new XProgram(processes.build()); //preludeBuilder.build(), processes.build(), postludeBuilder.build());
    }

    //private final XPreProcessBuilder preludeBuilder;
    //private final XPostProcessBuilder postludeBuilder;
    //private boolean startedProcessesDefinition = false;
    //public void beginPreludeDefinition() {
    //    if (currentProcess != null || startedProcessesDefinition) {
    //        throw new IllegalStateException("Attempt to start prelude definition while another process '"
    //                + currentProcess.getProcessId() + "'; is being constructed");
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
    //                + currentProcess.getProcessId() + "'; is being constructed");
    //    }
    //    if (!startedProcessesDefinition) {
    //        throw new IllegalStateException("Attempt to start postlude definition before definition of the processes");
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
                    "' definition while another process '" + currentProcess.getProcessId() +
                    "'; is being constructed");
        }
        currentProcess = new XProcessBuilder(processId);
    }

    public void finishProcessDefinition() {
        if (currentProcess == null) {
            throw new IllegalStateException("Attempt to end process definition without starting it");
        }
        processes.add(currentProcess.build());
        currentProcess = null;
        //if (currentProcess instanceof XProcessBuilder) {
        //    XParallelProcessBuilder currentParBuilder = (XParallelProcessBuilder) currentProcess;
        //    processes.add(currentParBuilder.build());
        //    currentProcess = null;
        //}
        // for pre & post do nothing, because current is a reference to it
    }

    //public XBinaryOperationEvent emitComputationEvent(XOperator operator, XMemoryUnit leftOperand, XMemoryUnit rightOperand) {
    //    XLocalMemoryUnit left = leftOperand instanceof XLocalMemoryUnit
    //            ? (XLocalMemoryUnit) leftOperand
    //            : copyToLocalMemoryIfNecessary(leftOperand);
    //    XLocalMemoryUnit right = rightOperand instanceof XLocalMemoryUnit
    //            ? (XLocalMemoryUnit) rightOperand
    //            : copyToLocalMemoryIfNecessary(rightOperand);
    //    return currentProcess.emitComputationEvent(operator, left, right);
    //}

    public XLocalMemoryUnit copyToLocalMemory(XSharedMemoryUnit shared) {
        XLocalMemoryUnit tempLocal = memoryManager.newLocalMemoryUnit();
        currentProcess.emitMemoryEvent(tempLocal, shared);
        return tempLocal;
    }

    public XLocalMemoryUnit copyToLocalMemoryIfNecessary(XMemoryUnit sharedOrLocal) {
        if (sharedOrLocal instanceof XSharedMemoryUnit) {
            copyToLocalMemory((XSharedMemoryUnit) sharedOrLocal);
        }
        else if (sharedOrLocal instanceof XLocalMemoryUnit) {
            return (XLocalMemoryUnit) sharedOrLocal;
        }
        throw new IllegalArgumentException(sharedOrLocal.getClass().getName());
    }

    //public XMemoryEvent emitMemoryEvent(XMemoryUnit source) {
    //    return emitMemoryEvent(memoryManager.newLocalMemoryUnit(), source);
    //}
    //
    //public XMemoryEvent emitMemoryEvent(XMemoryUnit destination, XMemoryUnit source) {
    //    XLocalMemoryUnit destinationLocal = destination instanceof XLocalMemoryUnit
    //            ? (XLocalMemoryUnit) destination
    //            : null;
    //    XLocalMemoryUnit sourceLocal = source instanceof XLocalMemoryUnit
    //            ? (XLocalMemoryUnit) source
    //            : null;
    //    XLocalMemoryUnit destinationShared = destination instanceof XLocalMemoryUnit
    //            ? (XLocalMemoryUnit) destination
    //            : null;
    //    XLocalMemoryUnit sourceShared = source instanceof XLocalMemoryUnit
    //            ? (XLocalMemoryUnit) source
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
    //            XLocalMemoryUnit tempLocal = copyToLocalMemoryIfNecessary(sourceShared);
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
