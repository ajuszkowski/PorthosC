package mousquetaires.languages.ytree.signatures;

import mousquetaires.languages.common.types.YXType;


public class MethodSignature {
    public final String name;
    public final YXType type;

    public MethodSignature(String name, YXType type) {
        this.name = name;
        this.type = type;
    }
}
