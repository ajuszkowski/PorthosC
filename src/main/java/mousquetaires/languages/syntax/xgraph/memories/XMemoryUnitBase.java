package mousquetaires.languages.syntax.xgraph.memories;


// Note: here the 'memoryevents' does not signifies the RAM memoryevents, it's just a storage
// containing some value (e.g. shared memoryevents = RAM, or local memoryevents = register)
public abstract class XMemoryUnitBase implements XMemoryUnit {

    private final String name;
    private final Bitness bitness;

    XMemoryUnitBase(String name, Bitness bitness) {
        this.name = name;
        this.bitness = bitness;
    }

    public String getName() {
        return name;
    }

    @Override
    public Bitness getBitness() {
        return bitness;
    }

    @Override
    public String toString() {
        return getName();// + " " + getBitness();
    }

    // todo: hashcode
    

}
