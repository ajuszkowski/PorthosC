package mousquetaires.languages.internalrepr.variables;

import mousquetaires.languages.internalrepr.types.InternalType;


public class InternalVariable extends InternalLvalueExpression {

    // TODO: add kind
    public enum Kind {
        Local,
        Global,
    }

    public final Kind kind = Kind.Local;
    public final String name;
    public final InternalType type;

    public InternalVariable(InternalType type, String name) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        String prefix = kind == Kind.Local ? "%" : "@";
        return prefix + name + ": " + type;
    }
}
