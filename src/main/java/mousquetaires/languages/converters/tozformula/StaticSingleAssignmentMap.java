package mousquetaires.languages.converters.tozformula;

import com.microsoft.z3.Context;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XLvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
import mousquetaires.utils.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


class StaticSingleAssignmentMap {

    private final Context ctx;
    private final XMemoryUnitCollector memoryUnitCollector;
    private final Map<XEvent, VarRefCollection> eventVarMap;

    StaticSingleAssignmentMap(Context ctx, int initialCapacity, Set<XEvent> entryEvents) {
        this.ctx = ctx;
        this.eventVarMap = new HashMap<>(initialCapacity);
        for (XEvent entryEvent : entryEvents) {
            this.eventVarMap.put(entryEvent, new VarRefCollection(entryEvent.getProcessId(), ctx));
        }
        this.memoryUnitCollector = new XMemoryUnitCollector();
    }

    // TODO: Check merge points !!!

    public void updateRefs(XEvent child, XEvent parent) {
        assert eventVarMap.containsKey(parent) : "access not in topological order: " + child + ", " + parent;
        VarRefCollection parentVarRefs = eventVarMap.get(parent);
        VarRefCollection childVarRefs = eventVarMap.getOrDefault(child, VarRefCollection.copy(parentVarRefs));
        for (XMemoryUnit memoryUnit : child.accept(memoryUnitCollector)) {
            if (memoryUnit instanceof XLvalueMemoryUnit) {
                XLvalueMemoryUnit lvalue = (XLvalueMemoryUnit) memoryUnit;
                if (!childVarRefs.containsVarRef(lvalue)) {
                    childVarRefs.addNew(lvalue);
                }
                else {
                    int index = Integer.max(childVarRefs.getRefIndex(lvalue), parentVarRefs.getRefIndex(lvalue));
                    childVarRefs.resetRef(lvalue, index);
                }
            }
        }
        eventVarMap.put(child, childVarRefs);
    }

    //void copyValues(Set<XEvent> parents, XEvent child) {
    //    if (parents.size() == 1) {
    //        XEvent singleParent = CollectionUtils.getSingleElement(parents);
    //        assert eventVarMap.containsKey(singleParent) :
    //                "access not in topological order: " + child + ", " + singleParent;
    //        //if (!eventVarMap.containsKey(singleParent)) { //TODO: remove this check
    //        //    assert singleParent instanceof XEntryEvent : singleParent + ", " + this;
    //        //    eventVarMap.put(singleParent, new VarRefCollection());
    //        //}
    //        if (eventVarMap.containsKey(child)) {
    //            // current child is the merge point visited for not first time
    //            //childRefs.addAll(eventVarMap.get(child));
    //            throw new IllegalStateException("values must be copied only once: " + child);
    //        }
    //        VarRefCollection childRefs = VarRefCollection.copy(eventVarMap.get(singleParent));
    //        for (XMemoryUnit memoryUnit : child.accept(memoryUnitCollector)) {
    //            if (memoryUnit instanceof XLvalueMemoryUnit) {
    //                XLvalueMemoryUnit lvalue = (XLvalueMemoryUnit) memoryUnit;
    //                if (!childRefs.containsVarRef(lvalue)) {
    //                    childRefs.addNew(lvalue);
    //                }
    //            }
    //        }
    //        eventVarMap.put(child, childRefs);
    //    }
    //    else {
    //        // iterate over all refs of all parents,
    //        // if there some parents have different refs of same var,
    //        // create the new ref of this var for the child
    //        VarRefCollection childRefs = new VarRefCollection(child.getProcessId(), ctx);
    //        for (XEvent parent : parents) {
    //            assert eventVarMap.containsKey(parent) : "access not in topological order: " + child + ", " + parent;
    //            VarRefCollection parentVarRefs = eventVarMap.get(parent);
    //            Set<XLvalueMemoryUnit> parentVars = parentVarRefs.getVars();
    //            for (XLvalueMemoryUnit var : parentVars) {
    //                if (childRefs.containsVarRef(var)) {
    //                    continue; //already set up
    //                }
    //                boolean isUniqueVar = true;
    //                int varIndex = parentVarRefs.getRefIndex(var);
    //                for (XEvent anotherParent : parents) {
    //                    if (anotherParent == parent) { continue; }
    //                    VarRefCollection anotherParentVarRefs = eventVarMap.get(anotherParent);
    //                    if (anotherParentVarRefs.containsVarRef(var)) {
    //                        isUniqueVar = false;
    //                        varIndex = Integer.max(varIndex, anotherParentVarRefs.getRefIndex(var));
    //                    }
    //                }
    //                if (isUniqueVar) {
    //                    varIndex++;
    //                }
    //                childRefs.add(var, varIndex);
    //            }
    //        }
    //        eventVarMap.put(child, childRefs);
    //    }
    //}

    VarRefCollection getEventMap(XEvent event) {
        assert eventVarMap.containsKey(event) : "access not in registered event: " + event;
        return eventVarMap.get(event);
    }

    // debug-method
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SSA{");
        sb.append("[").append(eventVarMap).append("]");
        sb.append('}');
        return sb.toString();
    }
}
