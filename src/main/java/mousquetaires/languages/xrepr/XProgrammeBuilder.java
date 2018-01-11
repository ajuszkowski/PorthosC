package mousquetaires.languages.xrepr;

import com.google.common.collect.ImmutableSet;
import mousquetaires.patterns.Builder;


public class XProgrammeBuilder extends Builder<XProgram> {

    private final ImmutableSet.Builder<XProcess> processes;

    public XProgrammeBuilder() {
        this.processes = new ImmutableSet.Builder<>();
    }

    public XProgram build() {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        setBuilt();
        return new XProgram(this);
    }

    public void addProcessus(XProcess process) {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        processes.add(process);
    }

    public ImmutableSet<XProcess> getProcesses() {
        if (!isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        return processes.build();
    }
}
