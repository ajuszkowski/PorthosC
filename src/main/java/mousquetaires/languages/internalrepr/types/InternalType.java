package mousquetaires.languages.internalrepr.types;

import mousquetaires.languages.internalrepr.InternalEntity;

import java.util.Objects;


/** Integer type as in LLVM: see https://llvm.org/docs/LangRef.html#integer-type */
public class InternalType implements InternalEntity {

    public static InternalType i0  = new InternalType(0);  // void
    public static InternalType i1  = new InternalType(1);
    public static InternalType i8  = new InternalType(8);
    public static InternalType i16 = new InternalType(16);
    public static InternalType i32 = new InternalType(32);
    public static InternalType i64 = new InternalType(64);

    public final int bitness;
    public final boolean signed;

    public InternalType(int bitness) {
        this(bitness, false);
    }

    public InternalType(int bitness, boolean signed) {
        this.bitness = bitness;
        this.signed = signed;
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
        if (!(o instanceof InternalType)) return false;
        InternalType that = (InternalType) o;
        return bitness == that.bitness &&
                signed == that.signed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bitness, signed);
    }
}
