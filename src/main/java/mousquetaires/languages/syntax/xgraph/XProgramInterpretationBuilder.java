package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryManager;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.languages.syntax.xgraph.process.interpretation.XProcessInterpreter;
import mousquetaires.utils.patterns.BuilderBase;


public class XProgramInterpretationBuilder extends BuilderBase<XProgram> {

    public XProcessInterpreter currentProcess;
    private ImmutableList.Builder<XProcess> processes;

    public XProgramInterpretationBuilder(XMemoryManager memoryManager) {
        this.memoryManager = memoryManager;
        this.processes = new ImmutableList.Builder<>();
        // TODO: add prelude and postlude processes!..
        //this.preludeBuilder = new XPreProcessBuilder(memoryManager);
        //this.postludeBuilder = new XPostProcessBuilder(memoryManager);
    }

    // TODO: publish methods also!
    private final XMemoryManager memoryManager;

    @Override
    public XProgram build() {
        markFinished();
        assert currentProcess == null : "process " + currentProcess.getProcessId() + " is not finished yet";
        return new XProgram(processes.build()); //preludeBuilder.build(), process.build(), postludeBuilder.build());
    }

    public void startProcessDefinition(String processId) {
        if (currentProcess != null) {
            throw new IllegalStateException("Attempt to start new process '" + processId +
                    "' definition while another process '" + currentProcess.getProcessId() +
                    "' is being constructed");
        }
        currentProcess = new XProcessInterpreter(processId, memoryManager);
        currentProcess.emitEntryEvent();
    }

    public void finishProcessDefinition() {
        if (currentProcess == null) {
            throw new IllegalStateException("Attempt to end process definition without starting it");
        }
        currentProcess.finish();
        processes.add(currentProcess.getResult());
        currentProcess = null;
    }
}
