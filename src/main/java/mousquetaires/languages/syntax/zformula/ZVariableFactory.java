package mousquetaires.languages.syntax.zformula;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;


public class ZVariableFactory {

    public static ZVariable createEventVariable(XEvent event) {
        return new ZVariable(event.toString());
    }

    public static ZConstant createConstantVariable(XMemoryUnit constant) {
        return new ZConstant(constant.getName());
    }

    public static ZVariableReference createMemoryUnitVariable(XMemoryUnit memoryUnit, int localityIndex) {
        return new ZVariableReference(memoryUnit.getName(), localityIndex);
    }
}
