package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariable;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;

import java.util.Objects;


public class YVariableDeclarationStatement extends YStatement {

    private final YType type;
    private final YVariable variable;

    public YVariableDeclarationStatement(CodeLocation location, YType type, YVariable variable) {
        this(location, newLabel(), type, variable);
    }

    private YVariableDeclarationStatement(CodeLocation location, String label, YType type, YVariable variable) {
        super(location, label);
        this.type = type;
        this.variable = variable;
    }

    public YType getType() {
        return type;
    }

    public YVariable getVariable() {
        return variable;
    }

    @Override
    public YVariableDeclarationStatement withLabel(String newLabel) {
        return new YVariableDeclarationStatement(codeLocation(), newLabel, getType(), getVariable());
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("declare(%s: %s);", getVariable(), getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YVariableDeclarationStatement)) return false;
        YVariableDeclarationStatement that = (YVariableDeclarationStatement) o;
        return Objects.equals(getType(), that.getType()) &&
                Objects.equals(getVariable(), that.getVariable());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getVariable());
    }
}
