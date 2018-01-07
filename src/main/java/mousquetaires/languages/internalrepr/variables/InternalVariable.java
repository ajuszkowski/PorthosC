package mousquetaires.languages.internalrepr.variables;

import mousquetaires.languages.internalrepr.expressions.InternalExpression;
import mousquetaires.languages.internalrepr.types.InternalType;

import java.util.Objects;


public class InternalVariable extends InternalExpression {

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
        return prefix + name + ":" + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InternalVariable)) return false;
        InternalVariable variable = (InternalVariable) o;
        return kind == variable.kind &&
                Objects.equals(name, variable.name) &&
                Objects.equals(type, variable.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, name, type);
    }
}
