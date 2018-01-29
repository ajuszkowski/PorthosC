package mousquetaires.languages.converters.toxrepr;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.syntax.xrepr.datamodels.DataModel;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XValue;
import mousquetaires.languages.syntax.xrepr.types.XType;
import mousquetaires.languages.syntax.ytree.expressions.YVariableRef;
import mousquetaires.utils.exceptions.xrepr.MemoryUnitDoubleDeclarationException;
import mousquetaires.utils.exceptions.xrepr.UndeclaredMemoryUnitException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for virtual memoryevents adresation (by variable names) and allocation.
 */
public final class XMemoryManager {

    private final Map<String, XLocalMemoryUnit> localLocations;
    private final Map<String, XSharedMemoryUnit> sharedLocations;

    XMemoryManager(ProgramLanguage language, DataModel dataModel) {
        // todo: use parameters for better memoryevents allocation
        this.localLocations = new HashMap<>();
        this.sharedLocations = new HashMap<>();
    }

    public XValue getConstantValue(Object value, XType type) {
        return new XValue(value, type);
    }

    public XLocalMemoryUnit newTempLocalMemoryUnit() {
        String name = newTempLocalName();
        while (localLocations.containsKey(name)) {
            name = newTempLocalName();
        }
        // TODO: consider returnType here!!!
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
    public XLocalMemoryUnit declareLocalMemoryUnit(String name, XType type) {
        if (isLocalMemoryDeclared(name)) {
            throw new MemoryUnitDoubleDeclarationException(name, true);
        }
        return newLocalMemory(name, type);
    }

    public XSharedMemoryUnit declareSharedMemoryUnit(String name, XType type) {
        if (isSharedMemoryDeclared(name)) {
            throw new MemoryUnitDoubleDeclarationException(name, false);
        }
        return newSharedMemory(name, type);
    }

    public XMemoryUnit declareMemoryUnit(YVariableRef variable, XType type) {
        YVariableRef.Kind kind = variable.getKind();
        String name = variable.getName();
        switch (kind) {
            case Local:
                return declareLocalMemoryUnit(name, type);
            case Global:
                return declareSharedMemoryUnit(name, type);
            default:
                throw new IllegalArgumentException(kind.name());
        }
    }

    //private String defineMemory(String postProcessId, XType returnType, boolean isLocal) {
    //    String baseName = postProcessId;
    //    int count = 1;
    //    XMemoryUnit location = null;
    //    while (location == null) {
    //        location = isLocal ? getLocalMemory(postProcessId) : tryFindSharedMemory(postProcessId);
    //        postProcessId = getNewName(baseName, count++);
    //    }
    //    return postProcessId;
    //}
    //

    private boolean isLocalMemoryDeclared(String name) {
        return localLocations.containsKey(name);
    }

    private boolean isSharedMemoryDeclared(String name) {
        return sharedLocations.containsKey(name);
    }

    private XLocalMemoryUnit newLocalMemory(String name, XType type) {
        XLocalMemoryUnit location = new XLocalMemoryUnit(name, type);
        localLocations.put(name, location);
        return location;
    }

    private XSharedMemoryUnit newSharedMemory(String name, XType type) {
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
