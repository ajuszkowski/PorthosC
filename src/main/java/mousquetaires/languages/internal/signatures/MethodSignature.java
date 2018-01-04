package mousquetaires.languages.internal.signatures;

import mousquetaires.languages.internal.types.Type;


public class MethodSignature {
    public final String name;
    public final Type type;

    public MethodSignature(String name, Type type) {
        this.name = name;
        this.type = type;
    }
}
