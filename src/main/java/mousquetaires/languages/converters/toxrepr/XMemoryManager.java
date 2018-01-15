package mousquetaires.languages.converters.toxrepr;

import mousquetaires.languages.types.YXType;
import mousquetaires.languages.syntax.xrepr.memory.XLocalMemory;
import mousquetaires.languages.syntax.xrepr.memory.XSharedMemory;
import mousquetaires.utils.exceptions.xrepr.MemoryUnitDoubleDeclarationException;
import mousquetaires.utils.exceptions.xrepr.UndeclaredMemoryUnitException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for virtual memory adresation (by variable names) and allocation.
 */
class XMemoryManager {

    private final Map<String, XLocalMemory> localLocations;
    private final Map<String, XSharedMemory> sharedLocations;

    XMemoryManager() {
        this.localLocations = new HashMap<>();
        this.sharedLocations = new HashMap<>();
    }

    public XLocalMemory tryFindLocalMemory(String name) {
        XLocalMemory result = localLocations.get(name);
        if (result == null) {
            // todo: probably, do not need to declare local memory (registers)
            throw new UndeclaredMemoryUnitException(name);
        }
        return result;
    }

    public XSharedMemory tryFindSharedMemory(String name) {
        XSharedMemory result = sharedLocations.get(name);
        if (result == null) {
            // todo: probably process undeclared shared memory units as well
            throw new UndeclaredMemoryUnitException(name);
        }
        return result;
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
