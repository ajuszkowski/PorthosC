package mousquetaires.languages.ytree.statements;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.expressions.YVariableRef;
import mousquetaires.languages.ytree.types.YType;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.Objects;


public class YVariableDeclarationStatement extends YStatement {

    public final YType type;
    public final YVariableRef variable;

    public YVariableDeclarationStatement(YType type, YVariableRef variable) {
        this(null, type, variable);
    }

    private YVariableDeclarationStatement(String label, YType type, YVariableRef variable) {
        super(label);
        this.type = type;
        this.variable = variable;
    }

    @Override
    public YVariableDeclarationStatement withLabel(String newLabel) {
        return new YVariableDeclarationStatement(newLabel, type, variable);
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(type, variable);
    }

    @Override
    public void accept(YtreeVisitor visitor) {
        visitor.visit(this);
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
