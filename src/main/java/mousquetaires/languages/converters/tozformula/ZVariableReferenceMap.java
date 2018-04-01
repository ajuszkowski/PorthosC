package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.zformula.*;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.HashMap;
import java.util.Map;

import static mousquetaires.languages.syntax.zformula.ZVariableFactory.createConstantVariable;
import static mousquetaires.languages.syntax.zformula.ZVariableFactory.createMemoryUnitVariable;


class ZVariableReferenceMap {

    private final Map<XLvalueMemoryUnit, Integer> variableRefMap;

    ZVariableReferenceMap() {
        this(new HashMap<>());
    }

    ZVariableReferenceMap(ZVariableReferenceMap other) {
        this(new HashMap<>(other.variableRefMap));
    }

    private ZVariableReferenceMap(Map<XLvalueMemoryUnit, Integer> variableRefMap) {
        this.variableRefMap = variableRefMap;
    }

    void addAllVariables(ZVariableReferenceMap other) {
        for (Map.Entry<XLvalueMemoryUnit, Integer> pair : other.variableRefMap.entrySet()) {
            XLvalueMemoryUnit memoryUnit = pair.getKey();
            int otherIndex = pair.getValue();
            int index = (!variableRefMap.containsKey(memoryUnit))
                    ? otherIndex
                    : Integer.max(otherIndex, variableRefMap.get(memoryUnit));
            variableRefMap.put(memoryUnit, index);
        }
    }

    void addVariableIfAbsent(XLvalueMemoryUnit memoryUnit) {
        if (!variableRefMap.containsKey(memoryUnit)) {
            variableRefMap.put(memoryUnit, 0);
        }
    }

    ZAtom updateReference(XLvalueMemoryUnit memoryUnit) {
        //if (memoryUnit instanceof XRvalueMemoryUnit) {
        //    throw new IllegalStateException("Cannot update reference for rvalue memory unit " + memoryUnit);
        //}
        int index = variableRefMap.getOrDefault(memoryUnit, 0) + 1;
        variableRefMap.put(memoryUnit, index);
        return createMemoryUnitVariable(memoryUnit, index);
    }

    ZAtom getReferenceOrThrow(XLocalMemoryUnit memoryUnit) {
        if (memoryUnit instanceof XRvalueMemoryUnit) {
            if (memoryUnit instanceof XConstant) {
                return createConstantVariable((XConstant) memoryUnit);
            }
            if (memoryUnit instanceof XComputationEvent) {
                throw new NotImplementedException(); // todo: cannot happen?
            }
            throw new NotImplementedException();
        }
        if (memoryUnit instanceof XLvalueMemoryUnit) {
            XLvalueMemoryUnit lvalue = (XLvalueMemoryUnit) memoryUnit;
            if (!variableRefMap.containsKey(lvalue)) {
                // TODO: more eloquent message
                throw new IllegalStateException("key " + memoryUnit + "not found");
            }
            return createMemoryUnitVariable(lvalue, variableRefMap.get(lvalue));
        }
        throw new NotImplementedException(); //?
    }

    @Override
    public String toString() {
        return "[" + variableRefMap + "]";
    }



}
