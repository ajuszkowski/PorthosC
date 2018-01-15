package mousquetaires.languages.syntax.xrepr.memory;

import mousquetaires.languages.types.YXType;


public class XLocalMemory extends XMemoryUnit {
    public XLocalMemory(String name, YXType type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return "%" + super.toString();
    }
}
