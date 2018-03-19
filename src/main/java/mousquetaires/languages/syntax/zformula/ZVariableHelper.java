package mousquetaires.languages.syntax.zformula;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;


public class ZVariableHelper {
    public static ZBoolVariable constructEventVariable(XEvent event) {
        //todo: use 'event.getLabel()', toString() is just for debugging
        return new ZBoolVariable(event.toString());//event.getLabel());
    }

    public static ZBoolVariable constructConstantVariable(XMemoryUnit constant) {
        return new ZBoolVariable(constant.getName());
    }

    public static ZVariableReference constructMemoryUnitVariable(XMemoryUnit memoryUnit, int localityIndex) {
        return new ZVariableReference(memoryUnit.getName(), localityIndex);
    }
}
