package mousquetaires.execution.memory;

public class SharedLocation extends MemoryLocation {
    public SharedLocation(String name) {
        super(name, Kind.SharedMemory);
    }
}
