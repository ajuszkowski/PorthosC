package mousquetaires.languages.common.types;


public class YXTypeFactory {
    public static YXType getPrimitiveType(YXTypeName name) {
        return getPrimitiveType(name, null, 0);
    }

    public static YXType getPrimitiveType(YXTypeName name, int pointerLevel) {
        return getPrimitiveType(name, null, pointerLevel);
    }

    public static YXType getPrimitiveType(YXTypeName name, YXTypeSpecifier typeSpecifier) {
        return getPrimitiveType(name, typeSpecifier, 0);
    }

    public static YXType getPrimitiveType(YXTypeName name, YXTypeSpecifier typeSpecifier, int pointerLevel) {
        if (typeSpecifier == null) {
            return new YXType(name.toString(), pointerLevel);
        }
        return new YXType(name.toString(), typeSpecifier, pointerLevel);
    }
}
