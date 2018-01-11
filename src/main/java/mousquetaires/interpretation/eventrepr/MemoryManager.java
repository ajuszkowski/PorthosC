package mousquetaires.interpretation.eventrepr;

import mousquetaires.languages.xrepr.memory.XRegistryLocation;
import mousquetaires.languages.xrepr.memory.XSharedLocation;

import java.util.HashMap;
import java.util.Map;


class MemoryManager {

    private final Map<String, XSharedLocation> sharedLocations;
    private final Map<String, XRegistryLocation> registryLocations;

    MemoryManager() {
        this.sharedLocations = new HashMap<>();
        this.registryLocations = new HashMap<>();
    }

    public XSharedLocation tryGetSharedLocation(String name) {
        return sharedLocations.get(name);
    }

    public XRegistryLocation tryGetRegistryLocation(String name) {
        return registryLocations.get(name);
    }

    public boolean tryRegisterSharedLocation(String name) {
        if (sharedLocations.containsKey(name)) {
            return false;
        }
        sharedLocations.put(name, new XSharedLocation(name));
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
        registryLocations.put(name, new XRegistryLocation(name));
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
