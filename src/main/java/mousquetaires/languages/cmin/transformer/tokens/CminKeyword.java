package mousquetaires.languages.cmin.transformer.tokens;

import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.types.YType;

import java.util.EnumMap;
import java.util.Map;


/**
 * Note, this class implements YEntity, although it does not belongs to the type system of Internal language.
 * This is done in order to simplify parsing types in CminToInternalTransformer visitor, where this Cmin-type will be
 * converted to Internal-type on the last stage, when the assignee identifier is determined.
 */
public enum CminKeyword implements YEntity {
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
     * YType:      C99 Minimum:
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
    private final static Map<CminKeyword, YType> bitnessMap =
            new EnumMap<CminKeyword, YType>(CminKeyword.class) {{
        put(Void,       YType.i0); // todo: set with respect to target machine bitness
        put(Bool,       YType.i1);
        put(Char,       YType.i8);
        put(Short,      YType.i16);
        put(Int,        YType.i16); // todo: set with respect to data model, sometimes int is 32-bit
        put(Long,       YType.i32);
        put(LongLong,   YType.i64);
        put(Float,      YType.i32);
        put(Double,     YType.i64);
        put(LongDouble, YType.i64);
    }};

    public static YType tryConvert(CminKeyword keyword) {
        return CminKeyword.bitnessMap.get(keyword);
    }

    public static YType convert(CminKeyword keyword) {
        YType result = tryConvert(keyword);
        if (result == null) {
            throw new IllegalArgumentException(keyword.name());
        }
        return result;
    }
}
