package mousquetaires.languages.syntax.xrepr.memories;


import mousquetaires.languages.syntax.xrepr.XEntity;
import mousquetaires.languages.types.YXType;

import java.util.Objects;

// Note: here the 'memoryevents' does not signifies the RAM memoryevents, it's just a storage
// containing some value (e.g. shared memoryevents = RAM, or local memoryevents = register)
public abstract class XMemoryUnit implements XEntity {

    public final String name;
    public final YXType type;

    public XMemoryUnit(String name, YXType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return name + ":" + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XMemoryUnit)) return false;
        XMemoryUnit location = (XMemoryUnit) o;
        return Objects.equals(name, location.name) &&
                Objects.equals(type, location.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
