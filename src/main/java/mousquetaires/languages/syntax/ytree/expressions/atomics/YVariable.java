package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;

import java.util.Objects;


// TODO: NOTE!!! IN FUNCTION INVOCATION FUNC. NAME IS YVariable!
public class YVariable extends YAtomBase {

    private final String name;

    public YVariable(String name) {
        this(Kind.Local, name, 0);
    }

    protected YVariable(Kind kind, String name, int pointerLevel) {
        super(kind, pointerLevel);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public YVariable asGlobal() {
        return new YVariable(Kind.Global, getName(), getPointerLevel());
    }

    @Override
    public YVariable withPointerLevel(int level) {
        return new YVariable(getKind(), getName(), level);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        String prefix = getKind() == Kind.Local ? "%" : "@";
        return prefix + getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof YVariable)) { return false; }
        if (!super.equals(o)) { return false; }
        YVariable that = (YVariable) o;
        return getPointerLevel() == that.getPointerLevel() &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getPointerLevel());
    }
}
