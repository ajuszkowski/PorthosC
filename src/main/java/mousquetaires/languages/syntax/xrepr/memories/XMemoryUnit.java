package mousquetaires.languages.syntax.xrepr.memories;


import mousquetaires.languages.syntax.xrepr.XEntity;
import mousquetaires.types.ZType;

import java.util.Objects;


// Note: here the 'memoryevents' does not signifies the RAM memoryevents, it's just a storage
// containing some value (e.g. shared memoryevents = RAM, or local memoryevents = register)
public abstract class XMemoryUnit implements XEntity {

    private final String name;
    private final ZType type;

    public XMemoryUnit(String name, ZType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ZType getType() {
        return type;
    }

    @Override
    public String toString() {
        return getName() + ":" + getType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XMemoryUnit)) return false;
        XMemoryUnit that = (XMemoryUnit) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType());
    }
}
