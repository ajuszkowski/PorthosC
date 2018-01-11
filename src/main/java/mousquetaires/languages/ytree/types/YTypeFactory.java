package mousquetaires.languages.ytree.types;


public class YTypeFactory {
    public static YType getPrimitiveType(YPrimitiveTypeName name) {
        return getPrimitiveType(name, null, 0);
    }

    public static YType getPrimitiveType(YPrimitiveTypeName name, int pointerLevel) {
        return getPrimitiveType(name, null, pointerLevel);
    }

    public static YType getPrimitiveType(YPrimitiveTypeName name, YPrimitiveTypeSpecifier typeSpecifier) {
        return getPrimitiveType(name, typeSpecifier, 0);
    }

    public static YType getPrimitiveType(YPrimitiveTypeName name, YPrimitiveTypeSpecifier typeSpecifier, int pointerLevel) {
        if (typeSpecifier == null) {
            return new YType(name.toString(), pointerLevel);
        }
        return new YType(name.toString(), typeSpecifier, pointerLevel);
    }
}
