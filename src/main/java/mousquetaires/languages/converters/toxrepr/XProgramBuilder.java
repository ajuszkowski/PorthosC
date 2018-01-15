package mousquetaires.languages.converters.toxrepr;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.syntax.xrepr.XControlFlowEvent;
import mousquetaires.languages.syntax.xrepr.XProcess;
import mousquetaires.languages.syntax.xrepr.XProgram;
import mousquetaires.languages.syntax.xrepr.events.XBarrierEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XMemoryEvent;
import mousquetaires.languages.syntax.xrepr.datamodels.DataModel;
import mousquetaires.utils.patterns.Builder;

// Interpreter of event representation
public class XProgramBuilder extends Builder<XProgram> {

    private XProcessBuilder currentBuilder;
    private final ImmutableSet.Builder<XProcess> processes;

    XProgramBuilder(ProgramLanguage language, DataModel dataModel) {
        this.processes = new ImmutableSet.Builder<>();
    }

    @Override
    public XProgram build() {
        if (isBuilt()) {
            throw new IllegalStateException(getAlreadyFinishedMessage());
        }
        setBuilt();
        return new XProgram(processes.build());
    }

    public void startProcessDefinition(String name) {
        if (currentBuilder != null) {
            throw new IllegalStateException("Cannot start new process definition while another process " +
                    currentBuilder.getName() + " is being constructed");
        }
        currentBuilder = new XProcessBuilder(name);
    }

    public void endProcessDefinition() {
        if (currentBuilder == null) {
            throw new IllegalStateException("Attempt to end process definition without starting it");
        }
        processes.add(currentBuilder.build());
        currentBuilder = null;
    }

    public void addMemoryEvent(XMemoryEvent event) {
        currentBuilder.addMemoryEvent(event);
    }

    public void addCallEvent(XControlFlowEvent event) {
        currentBuilder.addCallEvent(event);
    }

    public void addBarrierEvent(XBarrierEvent event) {
        currentBuilder.addBarrierEvent(event);
    }

}
