package mousquetaires.languages.converters.toxrepr;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.syntax.xrepr.datamodels.DataModel;
import mousquetaires.languages.syntax.xrepr.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XValue;
import mousquetaires.languages.types.YXType;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.utils.exceptions.xrepr.MemoryUnitDoubleDeclarationException;
import mousquetaires.utils.exceptions.xrepr.UndeclaredMemoryUnitException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for virtual memoryevents adresation (by variable names) and allocation.
 */
class XMemoryManager {

    private final Map<String, XLocalMemoryUnit> localLocations;
    private final Map<String, XSharedMemoryUnit> sharedLocations;

    XMemoryManager(ProgramLanguage language, DataModel dataModel) {
        // todo: use parameters for better memoryevents allocation
        this.localLocations = new HashMap<>();
        this.sharedLocations = new HashMap<>();
    }

    public XValue getConstantValue(Object value, YXType type) {
        return new XValue(value, type);
    }

    public XLocalMemoryUnit newTempLocalMemoryUnit() {
        String name = newTempLocalName();
        while (localLocations.containsKey(name)) {
            name = newTempLocalName();
        }
        // TODO: consider type here!!!
        return declareLocalMemoryUnit(name, null);
    }

    public XLocalMemoryUnit getLocalMemoryUnit(String name) {
        XLocalMemoryUnit result = localLocations.get(name);
        if (result == null) {
            // todo: probably, do not need to declare local memoryevents (registers)
            throw new UndeclaredMemoryUnitException(name);
        }
        return result;
    }

    public XSharedMemoryUnit getSharedMemoryUnit(String name) {
        XSharedMemoryUnit result = sharedLocations.get(name);
        if (result == null) {
            // todo: probably processName undeclared shared memoryevents units as well
            throw new UndeclaredMemoryUnitException(name);
        }
        return result;
    }

    // TODO: do we really need to declare local memory units? or we have infinitely many registers in our model?
    public XLocalMemoryUnit declareLocalMemoryUnit(String name, YXType type) {
        if (isLocalMemoryDeclared(name)) {
            throw new MemoryUnitDoubleDeclarationException(name, true);
        }
        return newLocalMemory(name, type);
    }

    public XSharedMemoryUnit declareSharedMemoryUnit(String name, YXType type) {
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
    //        location = isLocal ? getLocalMemory(name) : tryFindSharedMemory(name);
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

    private XLocalMemoryUnit newLocalMemory(String name, YXType type) {
        XLocalMemoryUnit location = new XLocalMemoryUnit(name, type);
        localLocations.put(name, location);
        return location;
    }

    private XSharedMemoryUnit newSharedMemory(String name, YXType type) {
        XSharedMemoryUnit location = new XSharedMemoryUnit(name, type);
        sharedLocations.put(name, location);
        return location;
    }



    private int tempNamesCounter = 1;
    private String newTempLocalName() {
        return getNewName("reg", tempNamesCounter++);
    }

    private String getNewName(String baseName, int count) {
        return baseName + '_' + count;
    }
}
