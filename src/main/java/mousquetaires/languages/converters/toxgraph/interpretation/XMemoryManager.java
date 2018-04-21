package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.memories.XLocalLvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XLocation;
import mousquetaires.languages.syntax.xgraph.memories.XLvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.memorymodels.relations.ZRelation;

import java.awt.peer.WindowPeer;
import java.util.HashMap;
import java.util.Map;

import static mousquetaires.utils.StringUtils.wrap;


public class XMemoryManager {

    private final Map<String, XLocation> sharedUnits;
    private final Map<XProcessId, Map<String, XRegister>> localUnitsMap;
    private XProcessId currentProcessId;
    private int tempRegisterNamesCounter;

    public XMemoryManager() {
        this.sharedUnits = new HashMap<>();
        this.localUnitsMap = new HashMap<>();
    }

    public void reset(XProcessId newProcessId) {
        currentProcessId = newProcessId;
        localUnitsMap.put(currentProcessId, new HashMap<>());
        tempRegisterNamesCounter = 1;
    }

    public XLvalueMemoryUnit getDeclaredUnitOrNull(String name) {
        //TODO: in C, local contexts have more priority than global ones. But in our case, we determine type of variable dynamically during interpretation, therefore if a variable has ever been seen as shared, it must be shared!
        // TODO: this architecture sucks! a variable may change its kind during interpretation and thus break consistency.
        // What we need is to have a pre-processor for each converters:
        // 1) Y->X: it pre-parses sources in order to find and remember jumps
        // 2) X->Z: it pre-watches the Y-tree in order to determine types of variables !
        if (sharedUnits.containsKey(name)) {
            currentLocalUnitsMap().remove(name);//remove it from locals if it is there
            return sharedUnits.get(name);
        }
        if (currentLocalUnitsMap().containsKey(name)) {
            assert !sharedUnits.containsKey(name) : name; //for confidence. // TODO: this is a debugging assert
            return currentLocalUnitsMap().get(name);
        }
        return null;
    }

    public XRegister getDeclaredRegister(String name, XProcessId processId) {
        if (!localUnitsMap.containsKey(processId)) {
            throw new IllegalArgumentException("Unregistered process: " + wrap(processId));
        }
        Map<String, XRegister> map = localUnitsMap.get(processId);
        if (!map.containsKey(name)) {
            throw new IllegalArgumentException("Unregistered local memory unit " + wrap(name) +
                                                       " for the process " + wrap(processId));
        }
        return map.get(name);
    }

    public XLvalueMemoryUnit declareUnresolvedUnit(String name, boolean isShared) {
        Type type = Type.int32; // TODO: create 'Undefined type' instead
        return isShared
                ? declareLocationImpl(name, type, false)
                : declareRegisterImpl(name, type, false);
    }

    public XRegister declareRegister(String name, Type type) {
        return declareRegisterImpl(name, type, true);
    }

    public XLocation declareLocation(String name, Type type) {
        return declareLocationImpl(name, type, true);
    }

    public XRegister newTempRegister(Type type) {
        String tempName = newTempRegisterName();
        Map<String, XRegister> map = currentLocalUnitsMap();
        while (map.containsKey(tempName)) {
            tempName = newTempRegisterName();
        }
        return declareRegister(tempName, type);
    }

    private XRegister declareRegisterImpl(String name, Type type, boolean isResolved) {
        XRegister register = new XRegister(name, type, currentProcessId, isResolved);
        Map<String, XRegister> map = currentLocalUnitsMap();
        assert !map.containsKey(name) : "attempt to register the local variable twice: " + name;
        map.put(name, register);
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

    private Map<String, XRegister> currentLocalUnitsMap() {
        Map<String, XRegister> result = localUnitsMap.get(currentProcessId);
        assert result != null;
        return result;
    }

    private String newTempRegisterName() {
        return getRegisterName(tempRegisterNamesCounter++);
    }

    private String getRegisterName(int count) {
        return "temp_reg_" + count;
    }
}
