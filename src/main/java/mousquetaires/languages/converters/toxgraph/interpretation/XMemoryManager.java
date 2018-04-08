package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.memories.XLocation;
import mousquetaires.languages.syntax.xgraph.memories.XLvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
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

    public XLvalueMemoryUnit getUnit(String name) {
        //as in C, local variables has more priority than global ones
        if (localUnits.containsKey(name)) {
            assert !sharedUnits.containsKey(name) : name; //for confidence. // TODO: this is a debugging assert
            return localUnits.get(name);
        }
        if (sharedUnits.containsKey(name)) {
            return sharedUnits.get(name);
        }
        throw new XUndeclaredMemoryUnitError(name);
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
        XRegister register = new XRegister(name, type, currentProcessId);
        assert !localUnits.containsKey(name) : "attempt to register the local variable twice: " + name;
        localUnits.put(name, register);
        return register;
    }

    public XLocation declareLocation(String name, Type type) {
        XLocation location = new XLocation(name, type);
        assert !sharedUnits.containsKey(name) : "attempt to register the shared variable twice: " + name;
        sharedUnits.put(name, location);
        return location;
    }

    public XRegister getTempRegister(Type type) {
        String tempName = newTempRegisterName();
        while (localUnits.containsKey(tempName)) {
            tempName = newTempRegisterName();
        }
        return declareRegister(tempName, type);
    }


    private String newTempRegisterName() {
        return getRegisterName(tempRegisterNamesCounter++);
    }

    private String getRegisterName(int count) {
        return "temp_reg_" + count;
    }
}
