package mousquetaires.interpretation.eventrepr;

import mousquetaires.languages.eventrepr.memory.XMemoryLocation;
import mousquetaires.languages.eventrepr.memory.XRegistryLocation;
import mousquetaires.languages.eventrepr.memory.XSharedLocation;
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

    public void createMemoryLocation(String name, XMemoryLocation.Kind kind) {
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

    public XMemoryLocation tryGetMemoryLocation(String name) {
        XSharedLocation sharedLocation = memoryManager.tryGetSharedLocation(name);
        if (sharedLocation != null) {
            return sharedLocation;
        }
        XRegistryLocation registryLocation = memoryManager.tryGetRegistryLocation(name);
        if (registryLocation != null) {
            return registryLocation;
        }
        return null;
    }

}
