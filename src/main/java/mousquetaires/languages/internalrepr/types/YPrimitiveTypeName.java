package mousquetaires.languages.internalrepr.types;

import mousquetaires.languages.internalrepr.YEntity;


public enum YPrimitiveTypeName implements YEntity {
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
    ;

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        String space = "";
        for (char c : this.name().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                builder.append(c);
            }
            else {
                builder.append(space).append(Character.toLowerCase(c));
                space = " ";
            }
        }
        return builder.toString();
    }
}
