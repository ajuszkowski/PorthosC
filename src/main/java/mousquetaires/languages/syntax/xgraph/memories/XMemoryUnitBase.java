package mousquetaires.languages.syntax.xgraph.memories;


import java.util.Objects;


// Note: here the 'memoryevents' does not signifies the RAM memoryevents, it's just a storage
// containing some value (e.g. shared memoryevents = RAM, or local memoryevents = register)
public abstract class XMemoryUnitBase implements XMemoryUnit {

    private final String name;
    private final Bitness bitness;

    XMemoryUnitBase(String name, Bitness bitness) {
        this.name = name;
        this.bitness = bitness;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSmtLabel() {
        return getName(); //TODO: add bitness to the smt label
    }

    @Override
    public Bitness getBitness() {
        return bitness;
    }

    @Override
    public String toString() {
        return getSmtLabel();
    }

    // todo: hashcode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XMemoryUnitBase)) return false;
        XMemoryUnitBase that = (XMemoryUnitBase) o;
        return Objects.equals(getName(), that.getName()) &&
                getBitness() == that.getBitness();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getBitness());
    }
}
