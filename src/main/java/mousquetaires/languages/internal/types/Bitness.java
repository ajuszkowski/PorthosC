package mousquetaires.languages.internal.types;

public enum Bitness {
    bit8  (8),
    bit16 (16),
    bit32 (32),
    bit64 (64),
    ;

    public final int value;

    Bitness(int value) {
        this.value = value;
    }

    public static Bitness parse(int bitnessNumerical) {
        for (Bitness bitness : Bitness.values()) {
            if (bitness.value == bitnessNumerical) {
                return bitness;
            }
        }
        throw new IllegalArgumentException("" + bitnessNumerical);
    }

    public static Bitness getMinBitness() {
        Bitness minBitness = bit8;
        for (Bitness bitness : values()) {
            if (minBitness.value > bitness.value) {
                minBitness = bitness;
            }
        }
        return minBitness;
    }

    public static Bitness getMaxBitness() {
        Bitness maxBitness = bit64;
        for (Bitness bitness : values()) {
            if (maxBitness.value < bitness.value) {
                maxBitness = bitness;
            }
        }
        return maxBitness;
    }
}
