package mousquetaires.interpretation;

import mousquetaires.execution.memory.MemoryLocation;
import mousquetaires.execution.memory.RegistryLocation;
import mousquetaires.execution.memory.SharedLocation;
import mousquetaires.languages.ProgramLanguage;


public class Interpreter {

    private final MemoryManager memoryManager;
    private final EventManager eventManager;
    private final TypeManager typeManager;

    public Interpreter(ProgramLanguage language) {
        this.memoryManager = new MemoryManager();
        this.eventManager  = new EventManager();
        this.typeManager   = new TypeManager(language);
    }

    public void createMemoryLocation(String name, MemoryLocation.Kind kind) {
        switch (kind) {
            case Registry:
                memoryManager.registerRegistryLocation(name);
                break;
            case SharedMemory:
                memoryManager.registerSharedLocation(name);
                break;
            default:
                throw new IllegalArgumentException(kind.toString());
        }
    }

    public MemoryLocation tryGetMemoryLocation(String name) {
        SharedLocation sharedLocation = memoryManager.tryGetSharedLocation(name);
        if (sharedLocation != null) {
            return sharedLocation;
        }
        RegistryLocation registryLocation = memoryManager.tryGetRegistryLocation(name);
        if (registryLocation != null) {
            return registryLocation;
        }
        return null;
    }

}
