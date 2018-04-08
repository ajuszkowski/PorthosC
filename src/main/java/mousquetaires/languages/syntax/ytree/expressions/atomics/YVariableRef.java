package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignee;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;
import java.util.Objects;

// TODO: NOTE!!! IN FUNCTION INVOCATION FUNC. NAME IS YVariableRef!
public class YVariableRef implements YAssignee {

    // TODO: add kind
    public enum Kind {
        Local,
        Global,
        ;

        public YVariableRef createVariable(String name) {
            return new YVariableRef(this, name);
        }
    }

    private final Kind kind;
    private final String name;

    private YVariableRef(Kind kind, String name) {
        this.name = name;
        this.kind = kind;
    }

    public YVariableRef withKind(Kind kind) {
        return kind.createVariable(getName());
    }

    public Kind getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }

    public boolean isGlobal() {
        return getKind() == Kind.Global;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        String prefix = kind == Kind.Local ? "%" : "@";
        return prefix + name;
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
