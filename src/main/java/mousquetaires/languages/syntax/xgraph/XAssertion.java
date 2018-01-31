package mousquetaires.languages.syntax.xgraph;

import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnitBase;


public class XAssertion implements XEntity {
    private final XMemoryUnitBase memoryUnit;
    private final XRegister value;

    public XAssertion(XMemoryUnitBase memoryUnit, XRegister value) {
        this.memoryUnit = memoryUnit;
        this.value = value;
    }

    public XMemoryUnitBase getMemoryUnit() {
        return memoryUnit;
    }

    public XRegister getValue() {
        return value;
    }
}
