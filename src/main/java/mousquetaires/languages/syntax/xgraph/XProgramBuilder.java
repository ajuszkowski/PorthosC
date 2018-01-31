package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryManager;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnitBase;
import mousquetaires.languages.syntax.xgraph.memories.XLocation;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;
import mousquetaires.languages.syntax.xgraph.processes.XProcessBuilder;
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
    //    XRegister left = leftOperand instanceof XRegister
    //            ? (XRegister) leftOperand
    //            : copyToLocalMemoryIfNecessary(leftOperand);
    //    XRegister right = rightOperand instanceof XRegister
    //            ? (XRegister) rightOperand
    //            : copyToLocalMemoryIfNecessary(rightOperand);
    //    return currentProcess.emitComputationEvent(operator, left, right);
    //}

    public XRegister copyToLocalMemory(XLocation shared) {
        XRegister tempLocal = memoryManager.newLocalMemoryUnit();
        currentProcess.emitMemoryEvent(tempLocal, shared);
        return tempLocal;
    }

    public XRegister copyToLocalMemoryIfNecessary(XMemoryUnitBase sharedOrLocal) {
        if (sharedOrLocal instanceof XLocation) {
            copyToLocalMemory((XLocation) sharedOrLocal);
        }
        else if (sharedOrLocal instanceof XRegister) {
            return (XRegister) sharedOrLocal;
        }
        throw new IllegalArgumentException(sharedOrLocal.getClass().getName());
    }

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
