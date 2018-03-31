package mousquetaires.languages.syntax.xgraph.memories;


import java.util.Objects;


// Note: here the 'memoryevents' does not signifies the RAM memoryevents, it's just a storage
// containing some value (e.g. shared memoryevents = RAM, or local memoryevents = register)
public abstract class XMemoryUnitBase implements XMemoryUnit {

    private final int uniqueId;
    private final String name;
    private final Bitness bitness;

    XMemoryUnitBase(String name, Bitness bitness) {
        this.name = name;
        this.bitness = bitness;
        this.uniqueId = createId();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Bitness getBitness() {
        return bitness;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof XMemoryUnitBase)) { return false; }
        XMemoryUnitBase that = (XMemoryUnitBase) o;
        return uniqueId == that.uniqueId &&
                Objects.equals(getName(), that.getName()) &&
                getBitness() == that.getBitness();
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, getName(), getBitness());
    }

    private static int lastUniqueId = 0;
    private static int createId() {
        return ++lastUniqueId;
    }
}
