package mousquetaires.languages.internalrepr.expressions.lvalue;

import mousquetaires.languages.internalrepr.expressions.InternalExpression;

import java.util.Objects;


public class InternalVariableRef extends InternalLvalueExpression {

    // TODO: add kind
    public enum Kind {
        Local,
        Global,
    }

    public final Kind kind = Kind.Local;
    public final String name;

    public InternalVariableRef(String name) {
        this.name = name;
    }

    private static int tempVariableCount = 1;
    public static InternalVariableRef newTempVariable() {
        return new InternalVariableRef("temp" + tempVariableCount++);
    }

    @Override
    public String toString() {
        String prefix = kind == Kind.Local ? "%" : "@";
        return prefix + name;  // + ":" + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InternalVariableRef)) return false;
        InternalVariableRef variable = (InternalVariableRef) o;
        return kind == variable.kind &&
                Objects.equals(name, variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, name);
    }
}
