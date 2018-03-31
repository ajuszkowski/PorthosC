package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;
import mousquetaires.languages.syntax.zformula.*;

import java.util.HashMap;
import java.util.Map;

import static mousquetaires.languages.syntax.zformula.ZVariableFactory.createConstantVariable;
import static mousquetaires.languages.syntax.zformula.ZVariableFactory.createMemoryUnitVariable;


class ZVariableReferenceMap {

    private final Map<XMemoryUnit, Integer> variableRefMap;
    private final ReferableMemoryUnitDetector detector;

    ZVariableReferenceMap() {
        this(new HashMap<>());
    }

    ZVariableReferenceMap(ZVariableReferenceMap other) {
        this(new HashMap<>(other.variableRefMap));
    }

    private ZVariableReferenceMap(Map<XMemoryUnit, Integer> variableRefMap) {
        this.variableRefMap = variableRefMap;
        this.detector = new ReferableMemoryUnitDetector();
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

    void addVariableIfAbsent(XMemoryUnit memoryUnit) {
        if (!variableRefMap.containsKey(memoryUnit)) {
            variableRefMap.put(memoryUnit, 0);
        }
    }

    ZFormula updateReference(XMemoryUnit memoryUnit) {
        if (isConstant(memoryUnit)) {
            throw new IllegalStateException("Cannot update reference for non-referable memory unit " + memoryUnit);
        }
        int index = variableRefMap.getOrDefault(memoryUnit, 0) + 1;
        variableRefMap.put(memoryUnit, index);
        return createMemoryUnitVariable(memoryUnit, index);
    }

    ZFormula getReferenceOrThrow(XMemoryUnit memoryUnit) {
        if (isConstant(memoryUnit)) {
            return createConstantVariable(memoryUnit);
        }
        if (!variableRefMap.containsKey(memoryUnit)) {
            // TODO: more eloquent message
            throw new IllegalStateException("key " + memoryUnit + "not found");
        }
        return createMemoryUnitVariable(memoryUnit, variableRefMap.get(memoryUnit));
    }

    @Override
    public String toString() {
        return "[" + variableRefMap + "]";
    }

    boolean isConstant(XMemoryUnit memoryUnit) {
        return !detector.isReferable(memoryUnit);
    }

    boolean isSharedMemory(XMemoryUnit memoryUnit) {
        return memoryUnit instanceof XSharedMemoryUnit;
    }

    private class ReferableMemoryUnitDetector implements XMemoryUnitVisitor<Boolean> {

        public boolean isReferable(XMemoryUnit memoryUnit) {
            return memoryUnit.accept(this);
        }

        @Override
        public Boolean visit(XRegister entity) {
            return true;
        }

        @Override
        public Boolean visit(XLocation entity) {
            return true;
        }

        @Override
        public Boolean visit(XConstant entity) {
            return false;
        }

        @Override
        public Boolean visit(XNullaryComputationEvent entity) {
            return false;
        }

        @Override
        public Boolean visit(XUnaryComputationEvent entity) {
            return false;
        }

        @Override
        public Boolean visit(XBinaryComputationEvent entity) {
            return false;
        }
    }
}
