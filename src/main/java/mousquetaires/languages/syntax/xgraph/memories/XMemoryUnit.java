package mousquetaires.languages.syntax.xgraph.memories;

import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;


public interface XMemoryUnit extends XEntity {

    String getName();

    Bitness getBitness();

    <T> T accept(XMemoryUnitVisitor<T> visitor);

    enum Bitness {
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
