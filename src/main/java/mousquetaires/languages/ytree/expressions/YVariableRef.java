package mousquetaires.languages.ytree.expressions;

import java.util.Objects;


public class YVariableRef extends YExpression {

    // TODO: add kind
    public enum Kind {
        Local,
        Global,
    }

    public final Kind kind = Kind.Local;
    public final String name;

    protected YVariableRef(String name) {
        this.name = name;
    }

    public static YVariableRef create(String name) {
        return new YVariableRef(name);
    }

    private static int tempVariableCount = 1;
    public static YVariableRef newTempVariable() {
        return new YVariableRef("temp" + tempVariableCount++);
    }

    @Override
    public String toString() {
        String prefix = kind == Kind.Local ? "%" : "@";
        return prefix + name;  // + ":" + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YVariableRef)) return false;
        YVariableRef variable = (YVariableRef) o;
        return kind == variable.kind &&
                Objects.equals(name, variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, name);
    }
}
