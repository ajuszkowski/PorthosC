package mousquetaires.languages.ytree.types;

import mousquetaires.languages.cmin.transformer.tokens.CminKeyword;
import mousquetaires.languages.ytree.YEntity;

import java.util.Objects;


/** Integer type as in LLVM: see https://llvm.org/docs/LangRef.html#integer-type */
public class YType implements YEntity {

    // TODO!!! remake it as close as possible to the smt representation

    public static YType i0  = new YType(0);  // void
    public static YType i1  = new YType(1);
    public static YType i8  = new YType(8);
    public static YType i16 = new YType(16);
    public static YType i32 = new YType(32);
    public static YType i64 = new YType(64);

    // TODO: bad design, operate 'int' and 'long' on Y-level as well (parametrised by bitness)
    public static YType getIntType() {
        return CminKeyword.convert(CminKeyword.Int);
    }

    public final int bitness;
    public final boolean signed;
    public final int pointerLevel;

    public YType(int bitness) {
        this(bitness, false, 0);
    }

    public YType(int bitness, boolean signed) {
        this(bitness, signed, 0);
    }

    public YType(int bitness, boolean signed, int pointerLevel) {
        this.bitness = bitness;
        this.signed = signed;
        this.pointerLevel = pointerLevel;
    }

    public YType withPointerLevel(int newPointerLevel) {
        return new YType(bitness, signed, newPointerLevel);
    }

    @Override
    public String toString() {
        //String signedPrefix = signed ? "" : "signed ";
        return "i" + bitness; // signedPrefix +
    }


    // see https://os.mbed.com/handbook/C-Data-Types
    // or http://en.cppreference.com/w/cpp/language/types


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YType)) return false;
        YType that = (YType) o;
        return bitness == that.bitness &&
                signed == that.signed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bitness, signed);
    }
}
