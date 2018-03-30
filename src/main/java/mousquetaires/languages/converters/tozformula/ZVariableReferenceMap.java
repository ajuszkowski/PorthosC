package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
import mousquetaires.languages.syntax.zformula.ZVariableReference;

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

    void addAllVariables(ZVariableReferenceMap other) {
        for (Map.Entry<XMemoryUnit, Integer> pair : other.variableRefMap.entrySet()) {
            XMemoryUnit memoryUnit = pair.getKey();
            int otherIndex = pair.getValue();
            int index = (!variableRefMap.containsKey(memoryUnit))
                    ? otherIndex
                    : Integer.max(otherIndex, variableRefMap.get(memoryUnit));
            variableRefMap.put(memoryUnit, index);
        }
    }

    void addVariableIfNotPresent(XMemoryUnit memoryUnit) {
        if (!variableRefMap.containsKey(memoryUnit)) {
            variableRefMap.put(memoryUnit, 0);
        }
    }

    void updateVariable(XMemoryUnit memoryUnit) {
        variableRefMap.put(memoryUnit, variableRefMap.get(memoryUnit) + 1);
    }

    ZVariableReference getReferenceOrThrow(XMemoryUnit memoryUnit) {
        if (!variableRefMap.containsKey(memoryUnit)) {
            // TODO: more eloquent message
            throw new IllegalStateException("key " + memoryUnit + "not found");
        }
        return new ZVariableReference(memoryUnit.getSmtLabel(), variableRefMap.get(memoryUnit));
    }

    @Override
    public String toString() {
        return "[" + variableRefMap + "]";
    }
}
