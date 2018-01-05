package mousquetaires.languages.internal.types;

import mousquetaires.languages.internal.InternalEntity;

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
        String signedPostfix = signed ? "" : "; signed";
        return "i" + bitness + signedPostfix;
    }


    // see https://os.mbed.com/handbook/C-Data-Types
    // or http://en.cppreference.com/w/cpp/language/types
}
