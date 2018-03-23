package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;

import java.util.HashMap;
import java.util.Map;


class ZVariableReferenceMap {

    private final Map<XMemoryUnit, Integer> variableRefMap;

    ZVariableReferenceMap() {
        this.variableRefMap = new HashMap<>();
    }

    ZVariableReferenceMap(ZVariableReferenceMap other) {
        this.variableRefMap = new HashMap<>(other.variableRefMap);
    }

    public void addAllVariables(ZVariableReferenceMap other) {
        for (Map.Entry<XMemoryUnit, Integer> pair : other.variableRefMap.entrySet()) {
            XMemoryUnit memoryUnit = pair.getKey();
            int otherIndex = pair.getValue();
            int index = (!variableRefMap.containsKey(memoryUnit))
                    ? otherIndex
                    : Integer.max(otherIndex, variableRefMap.get(memoryUnit));
            variableRefMap.put(memoryUnit, index);
        }
    }

    public void addVariableIfNotPresent(XMemoryUnit memoryUnit) {
        if (!variableRefMap.containsKey(memoryUnit)) {
            variableRefMap.put(memoryUnit, 0);
        }
    }

    public void updateVariable(XMemoryUnit memoryUnit) {
        variableRefMap.put(memoryUnit, variableRefMap.get(memoryUnit) + 1);
    }

    @Override
    public String toString() {
        return "[" + variableRefMap + "]";
    }
}
