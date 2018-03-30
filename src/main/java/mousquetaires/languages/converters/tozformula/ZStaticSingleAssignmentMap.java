package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;

import javax.management.StandardEmitterMBean;
import java.util.HashMap;
import java.util.Map;


class ZStaticSingleAssignmentMap {

    private final XMemoryUnitCollector memoryUnitCollector;
    private final Map<XEvent, ZVariableReferenceMap> eventVariableMap;
    private final ReferableMemoryUnitsDetector detector;

    ZStaticSingleAssignmentMap(int initialCapacity) {
        this.eventVariableMap = new HashMap<>(initialCapacity);
        this.memoryUnitCollector = new XMemoryUnitCollector();
        this.detector = new ReferableMemoryUnitsDetector();
    }

    void copyValues(XEvent parent, XEvent child) {
        if (!eventVariableMap.containsKey(parent)) {
            assert parent instanceof XEntryEvent : parent + ", " + this;
            eventVariableMap.put(parent, new ZVariableReferenceMap());
        }

        ZVariableReferenceMap childMap = new ZVariableReferenceMap(eventVariableMap.get(parent));
        if (eventVariableMap.containsKey(child)) {
            // current child is the merge point visited for not first time
            childMap.addAllVariables(eventVariableMap.get(child));
        }
        for (XMemoryUnit memoryUnit : child.accept(memoryUnitCollector)) {
            if (detector.isReferable(memoryUnit)) {
                childMap.addVariableIfNotPresent(memoryUnit);
                if (XMemoryUnitHelper.isSharedMemoryUnit(memoryUnit)) { // TODO: also do this with visitor
                    childMap.updateVariable(memoryUnit);
                }
            }
        }
        eventVariableMap.put(child, childMap);
    }

    ZVariableReferenceMap getEventMapOrThrow(XEvent event) {
        if (!eventVariableMap.containsKey(event)) {
            // TODO: more eloquent message
            throw new IllegalStateException("variable map for event " + event + " not found");
        }
        return eventVariableMap.get(event);
    }

    // debug-method
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SSA{");
        sb.append("[").append(eventVariableMap).append("]");
        sb.append('}');
        return sb.toString();
    }


    private class ReferableMemoryUnitsDetector implements XMemoryUnitVisitor<Boolean> {

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
