package mousquetaires.languages.syntax.xgraph.memories;


import mousquetaires.languages.common.Bitness;

import java.util.Objects;


// Note: here the 'memoryevents' does not signifies the RAM memoryevents, it's just a storage
// containing some value (e.g. shared memoryevents = RAM, or local memoryevents = register)
abstract class XLvalueMemoryUnitBase extends XMemoryUnitBase implements XLvalueMemoryUnit {

    private final int uniqueId;
    private final String name;

    XLvalueMemoryUnitBase(String name, Bitness bitness) {
        super(bitness);
        this.name = name;
        this.uniqueId = createId();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof XLvalueMemoryUnitBase)) { return false; }
        XLvalueMemoryUnitBase that = (XLvalueMemoryUnitBase) o;
        return uniqueId == that.uniqueId &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(uniqueId, getName());
    }

    private static int lastUniqueId = 0;
    private static int createId() {
        return ++lastUniqueId;
    }
}
