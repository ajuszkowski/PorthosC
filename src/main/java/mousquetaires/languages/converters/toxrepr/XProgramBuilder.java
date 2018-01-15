package mousquetaires.languages.converters.toxrepr;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.syntax.xrepr.XProcess;
import mousquetaires.languages.syntax.xrepr.XProgram;
import mousquetaires.languages.syntax.xrepr.events.XBarrierEvent;
import mousquetaires.languages.syntax.xrepr.events.controlflow.XControlFlowEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XMemoryEvent;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XValue;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.patterns.Builder;

class XProgramBuilder extends Builder<XProgram> {

    private XProcessBuilder processBuilder;
    private final ImmutableSet.Builder<XProcess> processes;

    XProgramBuilder() {
        this.processes = new ImmutableSet.Builder<>();
    }

    @Override
    public XProgram build() {
        if (isBuilt()) {
            throw new IllegalStateException(getAlreadyFinishedMessage());
        }
        finish();
        return new XProgram(processes.build());
    }

    public void startProcessDefinition(String name) {
        if (processBuilder != null) {
            throw new IllegalStateException("Cannot start new processName definition while another processName " +
                    processBuilder.getProcessName() + " is being constructed");
        }
        processBuilder = new XProcessBuilder(name);
    }

    public void endProcessDefinition() {
        if (processBuilder == null) {
            throw new IllegalStateException("Attempt to end processName definition without starting it");
        }
        processes.add(processBuilder.build());
        processBuilder = null;
    }

    // TODO: add registers initialisation to Cmin syntax
    public XMemoryEvent processInitialMemoryEvent(XLocalMemoryUnit destination, XValue source) {
        return processBuilder.addInitialWriteEvent(destination, source);
    }

    public XMemoryEvent processLocalMemoryEvent(XLocalMemoryUnit destination, XLocalMemoryUnit source) {
        return processBuilder.addLocalMemoryEvent(destination, source);
    }

    public XMemoryEvent processSharedMemoryEvent(XLocalMemoryUnit destination, XSharedMemoryUnit source) {
        return processBuilder.addSharedMemoryEvent(destination, source);
    }

    public XMemoryEvent processSharedMemoryEvent(XSharedMemoryUnit destination, XLocalMemoryUnit source) {
        return processBuilder.addSharedMemoryEvent(destination, source);
    }

    public XControlFlowEvent processControlFlowEvent() {
        throw new NotImplementedException();
    }

    public XBarrierEvent processBarrierEvent() {
        throw new NotImplementedException();
    }

    //public void addCallEvent(XControlFlowEvent event) {
    //    processBuilder.addCallEvent(event);
    //}
    //
    //public void addBarrierEvent(XBarrierEvent event) {
    //    processBuilder.addBarrierEvent(event);
    //}

}
