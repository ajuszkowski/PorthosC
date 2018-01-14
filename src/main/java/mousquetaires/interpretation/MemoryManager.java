package mousquetaires.interpretation;

import mousquetaires.languages.common.types.YXType;
import mousquetaires.languages.xrepr.memory.XLocalLocation;
import mousquetaires.languages.xrepr.memory.XSharedLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for virtual memory adresation (by variable names) and allocation.
 */
class MemoryManager {

    private final Map<String, XSharedLocation> sharedLocations;
    private final Map<String, XLocalLocation> localLocations;

    MemoryManager() {
        this.sharedLocations = new HashMap<>();
        this.localLocations = new HashMap<>();
    }

    public XSharedLocation tryGetSharedLocation(String name) {
        return sharedLocations.get(name);
    }

    public XLocalLocation tryGetLocalLocation(String name) {
        return localLocations.get(name);
    }

    public XSharedLocation defineSharedLocation(String name, YXType type) {
        String baseName = name;
        int count = 1;
        XSharedLocation location = tryGetSharedLocation(name);
        while (location != null) {
            name = getNewName(baseName, count++);
            location = tryGetSharedLocation(name);
        }
        location = new XSharedLocation(name, type);

        return newSharedLocation(name, type);
    }

    public XLocalLocation defineRegistryLocation(String name, YXType type) {
        String baseName = name;
        int count = 1;
        XLocalLocation location = null;
        while (location == null) {
            location = tryGetLocalLocation(name);
            name = getNewName(baseName, count++);
        }
        return newLocalLocation(name, type);
    }

    private XLocalLocation newLocalLocation(String name, YXType type) {
        XLocalLocation location = new XLocalLocation(name, type);
        localLocations.put(name, location);
        return location;
    }

    private XSharedLocation newSharedLocation(String name, YXType type) {
        XSharedLocation location = new XSharedLocation(name, type);
        sharedLocations.put(name, location);
        return location;
    }

    private static String getNewName(String baseName, int count) {
        return baseName + '_' + count;
    }
}
