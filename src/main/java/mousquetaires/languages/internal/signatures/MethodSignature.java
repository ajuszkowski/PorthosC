package mousquetaires.languages.internal.signatures;

import mousquetaires.languages.internal.types.InternalType;


public class MethodSignature {
    public final String name;
    public final InternalType type;

    public MethodSignature(String name, InternalType type) {
        this.name = name;
        this.type = type;
    }
}
