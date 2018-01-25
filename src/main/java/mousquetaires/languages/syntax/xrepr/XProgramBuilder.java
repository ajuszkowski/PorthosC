package mousquetaires.languages.syntax.xrepr;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.converters.toxrepr.XMemoryManager;
import mousquetaires.languages.syntax.xrepr.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xrepr.events.computation.XBinaryOperationEvent;
import mousquetaires.languages.syntax.xrepr.events.computation.XOperator;
import mousquetaires.languages.syntax.xrepr.events.controlflow.XControlFlowEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XMemoryEvent;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.xrepr.processes.*;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.patterns.Builder;

public class XProgramBuilder extends Builder<XProgram> {

    private final XMemoryManager memoryManager;

    private final XPreProcessBuilder preludeBuilder;
    private final XPostProcessBuilder postludeBuilder;

    private final ImmutableList.Builder<XParallelProcess> parallelProcesses;
    private XProcessBuilder currentBuilder;
    private boolean startedProcessesDefinition = false;

    public XProgramBuilder(XMemoryManager memoryManager) {
        this.memoryManager = memoryManager;
        this.parallelProcesses = new ImmutableList.Builder<>();
        this.preludeBuilder = new XPreProcessBuilder(memoryManager);
        this.postludeBuilder = new XPostProcessBuilder(memoryManager);
    }

    @Override
    public XProgram build() {
        finish();
        return new XProgram(preludeBuilder.build(), parallelProcesses.build(), postludeBuilder.build());
    }

    public void beginPreludeDefinition() {
        if (currentBuilder != null || startedProcessesDefinition) {
            throw new IllegalStateException("Attempt to start prelude definition while another process '"
                    + currentBuilder.getProcessId() + "'; is being constructed");
        }
        currentBuilder = preludeBuilder;
    }

    public void endPreludeDefinition() {
        endProcessDefinition();
    }

    public void beginProcessDefinition(int processId) {
        if (currentBuilder != null) {
            throw new IllegalStateException("Attempt to start new process '" + processId +
                    "' definition while another process '" + currentBuilder.getProcessId() +
                    "'; is being constructed");
        }
        currentBuilder = new XParallelProcessBuilder(processId, memoryManager);
        startedProcessesDefinition = true;
    }

    public void endProcessDefinition() {
        if (currentBuilder == null) {
            throw new IllegalStateException("Attempt to end process definition without starting it");
        }
        if (currentBuilder instanceof XParallelProcessBuilder) {
            XParallelProcessBuilder currentParBuilder = (XParallelProcessBuilder) currentBuilder;
            parallelProcesses.add(currentParBuilder.build());
            currentBuilder = null;
        }
        // for pre & post do nothing, because current is a reference to it
    }

    public void beginPostludeDefinition() {
        if (currentBuilder != null) {
            throw new IllegalStateException("Attempt to start postlude definition while another process '"
                    + currentBuilder.getProcessId() + "'; is being constructed");
        }
        if (!startedProcessesDefinition) {
            throw new IllegalStateException("Attempt to start postlude definition before definition of the processes");
        }
        currentBuilder = postludeBuilder;
    }

    public void endPostludeDefinition() {
        endProcessDefinition();
    }

    public XBinaryOperationEvent emitComputationEvent(XOperator operator, XMemoryUnit leftOperand, XMemoryUnit rightOperand) {
        XLocalMemoryUnit left = leftOperand instanceof XLocalMemoryUnit
                ? (XLocalMemoryUnit) leftOperand
                : moveToLocalMemoryIfNecessary((XSharedMemoryUnit) leftOperand);
        XLocalMemoryUnit right = rightOperand instanceof XLocalMemoryUnit
                ? (XLocalMemoryUnit) rightOperand
                : moveToLocalMemoryIfNecessary((XSharedMemoryUnit) rightOperand);
        return currentBuilder.emitComputationEvent(operator, left, right);
    }

    public XMemoryEvent emitMemoryEvent(XMemoryUnit destination, XMemoryUnit source) {
        XLocalMemoryUnit destinationLocal = destination instanceof XLocalMemoryUnit
                ? (XLocalMemoryUnit) destination
                : null;
        XLocalMemoryUnit sourceLocal = source instanceof XLocalMemoryUnit
                ? (XLocalMemoryUnit) source
                : null;
        XSharedMemoryUnit destinationShared = destination instanceof XSharedMemoryUnit
                ? (XSharedMemoryUnit) destination
                : null;
        XSharedMemoryUnit sourceShared = source instanceof XSharedMemoryUnit
                ? (XSharedMemoryUnit) source
                : null;

        if (destinationLocal != null) {
            if (sourceLocal != null) {
                return currentBuilder.emitMemoryEvent(destinationLocal, sourceLocal);
            }
            if (sourceShared != null) {
                return currentBuilder.emitMemoryEvent(destinationLocal, sourceShared);
            }
        }
        if (destinationShared != null) {
            if (sourceLocal != null) {
                return currentBuilder.emitMemoryEvent(destinationShared, sourceLocal);
            }
            if (sourceShared != null) {
                XLocalMemoryUnit tempLocal = moveToLocalMemoryIfNecessary(sourceShared);
                return currentBuilder.emitMemoryEvent(destinationShared, tempLocal);
            }
        }
        throw new IllegalStateException();
    }

    public XLocalMemoryUnit moveToLocalMemoryIfNecessary(XMemoryUnit sharedOrLocal) {
        if (sharedOrLocal instanceof XSharedMemoryUnit) {
            XSharedMemoryUnit shared = (XSharedMemoryUnit) sharedOrLocal;
            XLocalMemoryUnit tempLocal = memoryManager.newTempLocalMemoryUnit();
            currentBuilder.emitMemoryEvent(tempLocal, shared);
            return tempLocal;
        }
        if (sharedOrLocal instanceof XLocalMemoryUnit) {
            return (XLocalMemoryUnit) sharedOrLocal;
        }
        throw new IllegalArgumentException(sharedOrLocal.getClass().getName());
    }

    public XControlFlowEvent emitControlFlowEvent() {
        throw new NotImplementedException();
    }

    public XBarrierEvent emitBarrierEvent() {
        throw new NotImplementedException();
    }

    //public XAssertion addAssertion(XMemoryUnit memoryUnit, XValue value) {
    //    XAssertion assertion = new XAssertion(memoryUnit, value);
    //    postludeBuilder.emitComputationEvent()
    //    return assertion;
    //}

    //public void addCallEvent(XControlFlowEvent event) {
    //    currentBuilder.addCallEvent(event);
    //}
    //
    //public void addBarrierEvent(XBarrierEvent event) {
    //    currentBuilder.addBarrierEvent(event);
    //}

}
