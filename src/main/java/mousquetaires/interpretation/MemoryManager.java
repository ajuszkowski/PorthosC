package mousquetaires.interpretation;

import mousquetaires.interpretation.exceptions.MemoryUnitDoubleDeclarationException;
import mousquetaires.languages.common.types.YXType;
import mousquetaires.languages.xrepr.memory.XLocalMemory;
import mousquetaires.languages.xrepr.memory.XSharedMemory;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for virtual memory adresation (by variable names) and allocation.
 */
class MemoryManager {

    private final Map<String, XLocalMemory> localLocations;
    private final Map<String, XSharedMemory> sharedLocations;

    MemoryManager() {
        this.localLocations = new HashMap<>();
        this.sharedLocations = new HashMap<>();
    }

    public XLocalMemory tryFindLocalMemory(String name) {
        return localLocations.get(name);
    }

    public XSharedMemory tryFindSharedMemory(String name) {
        return sharedLocations.get(name);
    }

    public XLocalMemory declareLocalMemoryUnit(String name, YXType type) {
        if (isLocalMemoryDeclared(name)) {
            throw new MemoryUnitDoubleDeclarationException(name, true);
        }
        return newLocalMemory(name, type);
    }

    public XSharedMemory declareSharedMemoryUnit(String name, YXType type) {
        if (isSharedMemoryDeclared(name)) {
            throw new MemoryUnitDoubleDeclarationException(name, false);
        }
        return newSharedMemory(name, type);
    }

    //private String defineMemory(String name, YXType type, boolean isLocal) {
    //    String baseName = name;
    //    int count = 1;
    //    XMemoryUnit location = null;
    //    while (location == null) {
    //        location = isLocal ? tryFindLocalMemory(name) : tryFindSharedMemory(name);
    //        name = getNewName(baseName, count++);
    //    }
    //    return name;
    //}
    //

    private boolean isLocalMemoryDeclared(String name) {
        return localLocations.containsKey(name);
    }

    private boolean isSharedMemoryDeclared(String name) {
        return sharedLocations.containsKey(name);
    }

    private XLocalMemory newLocalMemory(String name, YXType type) {
        XLocalMemory location = new XLocalMemory(name, type);
        localLocations.put(name, location);
        return location;
    }

    private XSharedMemory newSharedMemory(String name, YXType type) {
        XSharedMemory location = new XSharedMemory(name, type);
        sharedLocations.put(name, location);
        return location;
    }

    //
    //private static String getNewName(String baseName, int count) {
    //    return baseName + '_' + count;
    //}
}
