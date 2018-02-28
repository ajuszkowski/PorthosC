package mousquetaires.languages.syntax.zformula;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;


public class ZVariableHelper {
    public static ZBoolVariableGlobal createEventVariable(XEvent event) {
        return new ZBoolVariableGlobal(event.getUniqueId());
    }

    public static ZVariableLocal createMemoryUnitVariable(XMemoryUnit memoryUnit, int localityIndex) {
        return new ZVariableLocal(memoryUnit.getName(), localityIndex);
    }
}
