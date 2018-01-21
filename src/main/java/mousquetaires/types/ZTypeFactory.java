package mousquetaires.types;


public class ZTypeFactory {
    public static ZType getPrimitiveType(ZTypeName name) {
        return getPrimitiveType(name, null, 0);
    }

    public static ZType getPrimitiveType(ZTypeName name, int pointerLevel) {
        return getPrimitiveType(name, null, pointerLevel);
    }

    public static ZType getPrimitiveType(ZTypeName name, ZTypeSpecifier typeSpecifier) {
        return getPrimitiveType(name, typeSpecifier, 0);
    }

    public static ZType getPrimitiveType(ZTypeName name, ZTypeSpecifier typeSpecifier, int pointerLevel) {
        if (typeSpecifier == null) {
            return new ZType(name.toString(), pointerLevel);
        }
        return new ZType(name.toString(), typeSpecifier, pointerLevel);
    }
}
