package mousquetaires.languages.syntax.ytree.statements.labeled;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YVariableRef;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.types.ZType;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.Objects;


public class YVariableDeclarationStatement extends YLabeledStatement {

    private final ZType type;
    private final YVariableRef variable;

    public YVariableDeclarationStatement(ZType type, YVariableRef variable) {
        this(null, type, variable);
    }

    private YVariableDeclarationStatement(String label, ZType type, YVariableRef variable) {
        super(label);
        this.type = type;
        this.variable = variable;
    }

    public ZType getType() {
        return type;
    }

    public YVariableRef getVariable() {
        return variable;
    }

    @Override
    public YVariableDeclarationStatement withLabel(String newLabel) {
        return new YVariableDeclarationStatement(newLabel, getType(), getVariable());
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(getType(), getVariable());
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YVariableDeclarationStatement copy() {
        return new YVariableDeclarationStatement(getLabel(), getType(), getVariable());
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
