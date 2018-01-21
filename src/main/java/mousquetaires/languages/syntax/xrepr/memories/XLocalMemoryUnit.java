package mousquetaires.languages.syntax.xrepr.memories;

import mousquetaires.types.ZType;


public class XLocalMemoryUnit extends XMemoryUnit {
    public XLocalMemoryUnit(String name, ZType type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return "%" + super.toString();
    }
}
