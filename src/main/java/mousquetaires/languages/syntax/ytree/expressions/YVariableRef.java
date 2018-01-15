package mousquetaires.languages.syntax.ytree.expressions;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.Objects;


public class YVariableRef extends YExpression {

    // TODO: add kind
    public enum Kind {
        Local,
        Global,
        ;
    }

    public final Kind kind = Kind.Local;
    public final String name;

    protected YVariableRef(String name) {
        // TODO: pass kind here also
        this.name = name;
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
