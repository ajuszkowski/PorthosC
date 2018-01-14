package mousquetaires.languages.xrepr.memory;

import mousquetaires.languages.common.types.YXType;


public class XLocalMemory extends XMemoryUnit {
    public XLocalMemory(String name, YXType type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return "%" + super.toString();
    }
}
