package mousquetaires.languages.syntax.xrepr.memories;

import mousquetaires.languages.syntax.xrepr.types.XType;


public class XSharedMemoryUnit extends XMemoryUnit {
    public XSharedMemoryUnit(String name, XType type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return "@" + super.toString();
    }
}
