package mousquetaires.languages.syntax.xrepr.memories;

import mousquetaires.languages.syntax.xrepr.types.XType;


public class XLocalMemoryUnit extends XMemoryUnit {
    public XLocalMemoryUnit(String name, XType type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return "%" + super.toString();
    }
}
