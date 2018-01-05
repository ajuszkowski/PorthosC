package mousquetaires.languages.cmin;

import mousquetaires.languages.internalrepr.InternalEntity;
import mousquetaires.languages.internalrepr.types.InternalType;

import java.util.EnumMap;
import java.util.Map;


/**
 * Note, this class extends InternalEntity, although it does not belongs to the type system of Internal language.
 * This is done in order to simplify parsing types in CminToInternalTransformer visitor, where this Cmin-type will be
 * converted to Internal-type on the last stage, when the variable identifier is determined.
 */
public enum CminKeyword implements InternalEntity {
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
     * InternalType:      C99 Minimum:
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
    private final static Map<CminKeyword, InternalType> bitnessMap =
            new EnumMap<CminKeyword, InternalType>(CminKeyword.class) {{
        put(Void,       InternalType.i0); // todo: set with respect to target machine bitness
        put(Bool,       InternalType.i1);
        put(Char,       InternalType.i8);
        put(Short,      InternalType.i16);
        put(Int,        InternalType.i16); // todo: set with respect to data model, sometimes int is 32-bit
        put(Long,       InternalType.i32);
        put(LongLong,   InternalType.i64);
        put(Float,      InternalType.i32);
        put(Double,     InternalType.i64);
        put(LongDouble, InternalType.i64);
    }};

    public static InternalType tryConvert(CminKeyword keyword) {
        return CminKeyword.bitnessMap.get(keyword);
    }
}
