package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;

import java.util.Objects;


// TODO: NOTE!!! IN FUNCTION INVOCATION FUNC. NAME IS YVariable!
public class YVariable extends YAtomBase {

    private final String name;

    public YVariable(CodeLocation location, String name) {
        this(location, Kind.Local, name, 0);
    }

    protected YVariable(CodeLocation location, Kind kind, String name, int pointerLevel) {
        super(location, kind, pointerLevel);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public YVariable asGlobal() {
        return new YVariable(codeLocation(), Kind.Global, getName(), getPointerLevel());
    }

    @Override
    public YVariable withPointerLevel(int level) {
        return new YVariable(codeLocation(), getKind(), getName(), level);
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
