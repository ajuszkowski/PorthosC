package mousquetaires.languages.internal.types;

public class BitvectorType extends Type {

    public final Bitness bitness;
    public final boolean signed;

    public BitvectorType(Bitness bitness, boolean signed) {
        this.bitness = bitness;
        this.signed = signed;
    }

    public BitvectorType(int bitnessNumerical, boolean signed) {
        this.bitness = Bitness.parse(bitnessNumerical);
        this.signed = signed;
    }

    // see https://os.mbed.com/handbook/C-Data-Types
    // or http://en.cppreference.com/w/cpp/language/types
}
