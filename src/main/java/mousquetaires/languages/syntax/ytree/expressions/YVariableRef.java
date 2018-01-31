package mousquetaires.languages.syntax.ytree.expressions;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignee;
import mousquetaires.languages.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.Objects;

// TODO: NOTE!!! IN FUNCTION INVOCATION FUNC. NAME IS YVariableRef!
public class YVariableRef implements YAssignee {

    // TODO: add kind
    public enum Kind {
        Local,
        Global,
        ;
    }

    private final Kind kind = Kind.Local;
    private final String name;

    public YVariableRef(String name) {
        // TODO: pass kind here also
        this.name = name;
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

    public static YVariableRef create(String name) {
        return new YVariableRef(name);
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YVariableRef copy() {
        return create(name); // TODO: processName kind here also
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
