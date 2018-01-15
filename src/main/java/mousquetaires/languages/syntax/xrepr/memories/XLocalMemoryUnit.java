package mousquetaires.languages.syntax.xrepr.memories;

import mousquetaires.languages.types.YXType;


public class XLocalMemoryUnit extends XMemoryUnit {
    public XLocalMemoryUnit(String name, YXType type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return "%" + super.toString();
    }
}
