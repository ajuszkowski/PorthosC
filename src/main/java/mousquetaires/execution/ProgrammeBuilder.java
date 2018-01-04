package mousquetaires.execution;

import com.google.common.collect.ImmutableSet;
import mousquetaires.patterns.Builder;


public class ProgrammeBuilder implements Builder<Programme> {

    private static final String FINISHED_MESSAGE = "Programme building has already built.";
    private static final String NOT_FINISHED_MESSAGE = "Programme building is not built yet.";

    private boolean built;
    private final ImmutableSet.Builder<Processus> processes;

    public ProgrammeBuilder() {
        this.processes = new ImmutableSet.Builder<>();
    }

    public Programme build() {
        if (built) {
            throw new RuntimeException(FINISHED_MESSAGE);
        }
        built = true;
        return new Programme(this);
    }

    public void addProcessus(Processus processus) {
        if (built) {
            throw new RuntimeException(FINISHED_MESSAGE);
        }
        processes.add(processus);
    }

    public ImmutableSet<Processus> getProcesses() {
        if (!built) {
            throw new RuntimeException(NOT_FINISHED_MESSAGE);
        }
        return processes.build();
    }
}
