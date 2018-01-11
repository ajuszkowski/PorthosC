package mousquetaires.languages.ytree.statements;

import mousquetaires.languages.ytree.expressions.YVariableRef;
import mousquetaires.languages.ytree.types.YType;

import java.util.Objects;


public class YVariableDeclarationStatement extends YStatement {

    public final YType type;
    public final YVariableRef variable;

    public YVariableDeclarationStatement(YType type, YVariableRef variable) {
        this.type = type;
        this.variable = variable;
    }

    @Override
    public String toString() {
        return String.format("declare(%s: %s);", variable, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YVariableDeclarationStatement)) return false;
        YVariableDeclarationStatement that = (YVariableDeclarationStatement) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(variable, that.variable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, variable);
    }
}
