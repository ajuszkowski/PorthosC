package mousquetaires.languages.syntax.xrepr;

import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XMemoryUnit;


public class XAssertion implements XEntity {
    private final XMemoryUnit memoryUnit;
    private final XLocalMemoryUnit value;

    public XAssertion(XMemoryUnit memoryUnit, XLocalMemoryUnit value) {
        this.memoryUnit = memoryUnit;
        this.value = value;
    }

    public XMemoryUnit getMemoryUnit() {
        return memoryUnit;
    }

    public XLocalMemoryUnit getValue() {
        return value;
    }
}
