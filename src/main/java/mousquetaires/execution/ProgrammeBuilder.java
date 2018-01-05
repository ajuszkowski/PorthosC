package mousquetaires.execution;

import com.google.common.collect.ImmutableSet;
import mousquetaires.patterns.Builder;


public class ProgrammeBuilder extends Builder<Programme> {

    private final ImmutableSet.Builder<Processus> processes;

    public ProgrammeBuilder() {
        this.processes = new ImmutableSet.Builder<>();
    }

    public Programme build() {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        setBuilt();
        return new Programme(this);
    }

    public void addProcessus(Processus processus) {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        processes.add(processus);
    }

    public ImmutableSet<Processus> getProcesses() {
        if (!isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        return processes.build();
    }
}
