package mousquetaires.languages.syntax.xrepr.memories;

import mousquetaires.types.ZType;


public class XSharedMemoryUnit extends XMemoryUnit {
    public XSharedMemoryUnit(String name, ZType type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return "@" + super.toString();
    }
}
