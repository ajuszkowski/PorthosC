package mousquetaires.languages.cmin;

import mousquetaires.languages.internal.AbstractEntity;
import mousquetaires.languages.internal.types.Bitness;

import java.util.EnumMap;
import java.util.Map;

/**
 * Note, this class implements AbstractEntity, although it does not belongs to the type system of Internal language.
 * This is done in order to simplify parsing types in CminToInternalTransformer visitor, where this Cmin-type will be
 * converted to Internal-type on the last stage, when the variable identifier is determined.
 */
public enum CminKeyword implements AbstractEntity {
    // typeSpecifier:
    Signed,
    Unsigned,
    // variableTypeQualifier:
    Const,
    Restrict,
    Volatile,
    Atomic,
    // primitiveTypeKeyword:
    Void,
    Char,
    Short,
    Int,
    Long,
    LongLong,
    Float,
    Double,
    LongDouble,
    Bool,
    //todo: more
    ;

    /**
     * https://stackoverflow.com/a/697531
     * Type:      C99 Minimum:
     * char       8
     * short      16
     * int        16 (however it's always 32)
     * long       32
     * long long  64
     * float      32
     * double     64
     * long double 80 (64)
     * see possible set of modifiers: https://en.wikipedia.org/wiki/C_data_types
     */
    public final static Map<CminKeyword, Bitness> bitnessMap = new EnumMap<CminKeyword, Bitness>(CminKeyword.class) {{
        put(Void,       Bitness.bit32); // todo: set with respect to target machine bitness
        put(Char,       Bitness.bit8);
        put(Short,      Bitness.bit16);
        put(Int,        Bitness.bit16); // todo: set with respect to data model, sometimes int is 32-bit
        put(Long,       Bitness.bit32);
        put(LongLong,   Bitness.bit64);
        put(Float,      Bitness.bit32);
        put(Double,     Bitness.bit64);
        put(LongDouble, Bitness.bit64);
    }};
}
