package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.memories.XLocation;
import mousquetaires.languages.syntax.xgraph.memories.XLvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.languages.syntax.xgraph.types.XMockType;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariableRef;
import mousquetaires.utils.exceptions.xgraph.XUndeclaredMemoryUnitError;

import java.util.HashMap;
import java.util.Map;


public class XMemoryManager {

    private final Map<String, XLocation> sharedUnits;
    private Map<String, XRegister> localUnits;
    private XProcessId currentProcessId;
    private int tempRegisterNamesCounter;

    public XMemoryManager() {
        this.sharedUnits = new HashMap<>();
    }

    public void reset(XProcessId newProcessId) {
        currentProcessId = newProcessId;
        localUnits = new HashMap<>();
        tempRegisterNamesCounter = 1;
    }

    public XLvalueMemoryUnit getDeclaredUnitOrNull(String name) {
        //as in C, local variables has more priority than global ones
        if (localUnits.containsKey(name)) {
            assert !sharedUnits.containsKey(name) : name; //for confidence. // TODO: this is a debugging assert
            return localUnits.get(name);
        }
        if (sharedUnits.containsKey(name)) {
            return sharedUnits.get(name);
        }
        return null;
    }

    public XLvalueMemoryUnit declareUnresolvedUnit(String name, boolean isShared) {
        Type type = Type.int32; // TODO: create 'Undefined type' instead
        return isShared
                ? declareLocationImpl(name, type, false)
                : declareRegisterImpl(name, type, false);
    }

    public XLocation redeclareAsSharedIfNeeded(String name) {
        if (sharedUnits.containsKey(name)) {
            return sharedUnits.get(name);
        }
        if (!localUnits.containsKey(name)) {
            throw new IllegalArgumentException("attempt to re-declare an unregistered memory unit: " + name);
        }
        XRegister removed = localUnits.remove(name);
        return declareLocation(name, removed.getType());
    }

    public XRegister declareRegister(String name, Type type) {
        return declareRegisterImpl(name, type, true);
    }

    public XLocation declareLocation(String name, Type type) {
        return declareLocationImpl(name, type, true);
    }

    public XRegister newTempRegister(Type type) {
        String tempName = newTempRegisterName();
        while (localUnits.containsKey(tempName)) {
            tempName = newTempRegisterName();
        }
        return declareRegister(tempName, type);
    }

    public void clearLocals() {
        localUnits.clear();
    }

    private XRegister declareRegisterImpl(String name, Type type, boolean isResolved) {
        XRegister register = new XRegister(name, type, currentProcessId, isResolved);
        assert !localUnits.containsKey(name) : "attempt to register the local variable twice: " + name;
        localUnits.put(name, register);
        return register;
    }

    private XLocation declareLocationImpl(String name, Type type, boolean isResolved) {
        XLocation location = new XLocation(name, type, isResolved);
        if (sharedUnits.containsKey(name)) {
            return sharedUnits.get(name);
        }
        sharedUnits.put(name, location);
        return location;
    }

    private String newTempRegisterName() {
        return getRegisterName(tempRegisterNamesCounter++);
    }

    private String getRegisterName(int count) {
        return "temp_reg_" + count;
    }
}
