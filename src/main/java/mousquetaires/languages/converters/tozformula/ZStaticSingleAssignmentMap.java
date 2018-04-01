package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.memories.*;

import java.util.HashMap;
import java.util.Map;


class ZStaticSingleAssignmentMap {

    private final XMemoryUnitCollector memoryUnitCollector;
    private final Map<XEvent, ZVariableReferenceMap> eventVariableMap;

    ZStaticSingleAssignmentMap(int initialCapacity) {
        this.eventVariableMap = new HashMap<>(initialCapacity);
        this.memoryUnitCollector = new XMemoryUnitCollector();
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
            if (memoryUnit instanceof XLvalueMemoryUnit) {
                childMap.addVariableIfAbsent((XLvalueMemoryUnit) memoryUnit);
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
}
