package mousquetaires.languages.syntax.xgraph.memories;


import mousquetaires.languages.syntax.xgraph.XEntity;


// Note: here the 'memoryevents' does not signifies the RAM memoryevents, it's just a storage
// containing some value (e.g. shared memoryevents = RAM, or local memoryevents = register)
public abstract class XMemoryUnit implements XEntity {

    private final String name;
    private final Bitness bitness;

    XMemoryUnit(String name, Bitness bitness) {
        this.name = name;
        this.bitness = bitness;
    }

    public String getName() {
        return name;
    }

    public Bitness getBitness() {
        return bitness;
    }

    @Override
    public String toString() {
        return getName() + " " + getBitness();
    }

    // todo: hashcode
    

    public enum Bitness {
        bit1,
        bit16,
        bit32,
        bit64,
        ;

        public int toInt() {
            switch (this) {
                case bit1:  return 1;
                case bit16: return 16;
                case bit32: return 32;
                case bit64: return 64;
                default:
                    throw new IllegalArgumentException(this.name());
            }
        }



        public static Bitness parseInt(int bitness) {
            for (Bitness bit : values()) {
                if (bit.toInt() == bitness) {
                    return bit;
                }
            }
            throw new IllegalArgumentException("" + bitness);
        }

        @Override
        public String toString() {
            return toInt() + "bit";
        }
    }
}
