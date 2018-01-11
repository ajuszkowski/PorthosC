package mousquetaires.languages.internalrepr.types;

import mousquetaires.languages.internalrepr.YEntity;


public enum YPrimitiveTypeSpecifier implements YEntity {
    Signed,
    Unsigned,
    ;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
