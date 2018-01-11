package mousquetaires.languages.ytree.types;

import mousquetaires.languages.ytree.YEntity;


public enum YPrimitiveTypeSpecifier implements YEntity {
    Signed,
    Unsigned,
    ;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
