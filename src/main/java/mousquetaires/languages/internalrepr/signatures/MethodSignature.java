package mousquetaires.languages.internalrepr.signatures;

import mousquetaires.languages.internalrepr.types.YType;


public class MethodSignature {
    public final String name;
    public final YType type;

    public MethodSignature(String name, YType type) {
        this.name = name;
        this.type = type;
    }
}
