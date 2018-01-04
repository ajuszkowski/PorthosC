package mousquetaires.interpretation;

import mousquetaires.execution.memory.MemoryLocation;
import mousquetaires.execution.memory.RegistryLocation;
import mousquetaires.execution.memory.SharedLocation;

import java.util.HashMap;
import java.util.Map;


class MemoryManager {

    private final Map<String, SharedLocation> sharedLocations;
    private final Map<String, RegistryLocation> registryLocations;

    MemoryManager() {
        this.sharedLocations = new HashMap<>();
        this.registryLocations = new HashMap<>();
    }

    public SharedLocation tryGetSharedLocation(String name) {
        return sharedLocations.get(name);
    }

    public RegistryLocation tryGetRegistryLocation(String name) {
        return registryLocations.get(name);
    }

    public boolean tryRegisterSharedLocation(String name) {
        if (sharedLocations.containsKey(name)) {
            return false;
        }
        sharedLocations.put(name, new SharedLocation(name));
        return true;
    }

    public void registerSharedLocation(String name) {
        String baseName = name;
        int count = 1;
        while (!tryRegisterSharedLocation(name)){
            name = baseName + '_' + count;
        }
    }

    public boolean tryRegisterRegistryLocation(String name) {
        if (registryLocations.containsKey(name)) {
            return false;
        }
        registryLocations.put(name, new RegistryLocation(name));
        return true;
    }

    public void registerRegistryLocation(String name) {
        String baseName = name;
        int count = 1;
        while (!tryRegisterRegistryLocation(name)){
            name = baseName + '_' + count;
        }
    }
}
