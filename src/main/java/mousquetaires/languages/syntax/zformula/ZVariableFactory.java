package mousquetaires.languages.syntax.zformula;

import mousquetaires.languages.common.Bitness;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLvalueMemoryUnit;


public class ZVariableFactory {

    public static ZGlobalVariable createEventVariable(XEvent event) {
        return new ZGlobalVariable(event.getName(), Bitness.bit1); // event smt-variables are boolean
    }

    public static ZConstant createConstantVariable(XConstant constant) {
        return new ZConstant(constant.getValue(), constant.getBitness());
    }

    public static ZIndexedVariable createMemoryUnitVariable(XLvalueMemoryUnit memoryUnit, int localityIndex) {
        return new ZIndexedVariable(memoryUnit.getName(), memoryUnit.getBitness(), localityIndex);
    }
}
