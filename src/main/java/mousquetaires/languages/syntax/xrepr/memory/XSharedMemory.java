package mousquetaires.languages.syntax.xrepr.memory;

import mousquetaires.languages.types.YXType;


public class XSharedMemory extends XMemoryUnit {
    public XSharedMemory(String name, YXType type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return "@" + super.toString();
    }
}
