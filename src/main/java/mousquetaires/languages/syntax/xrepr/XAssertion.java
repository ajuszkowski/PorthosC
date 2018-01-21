package mousquetaires.languages.syntax.xrepr;

import mousquetaires.languages.syntax.xrepr.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XValue;


public class XAssertion implements XEntity {
    private final XMemoryUnit memoryUnit;
    private final XValue value;

    public XAssertion(XMemoryUnit memoryUnit, XValue value) {
        this.memoryUnit = memoryUnit;
        this.value = value;
    }

    public XMemoryUnit getMemoryUnit() {
        return memoryUnit;
    }

    public XValue getValue() {
        return value;
    }
}
