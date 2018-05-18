package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;

import java.util.Objects;


// TODO: NOTE!!! IN FUNCTION INVOCATION FUNC. NAME IS YVariable!
public class YVariableRef extends YAtomBase {

    private final String name;

    public YVariableRef(CodeLocation location, String name) {
        this(location, Kind.Local, name, 0);
    }

    protected YVariableRef(CodeLocation location, String name, int pointerLevel) {
        this(location, Kind.Local, name, pointerLevel);
    }

    private YVariableRef(CodeLocation location, Kind kind, String name, int pointerLevel) {
        super(location, kind, pointerLevel);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public YVariableRef asGlobal() {
        return new YVariableRef(codeLocation(), Kind.Global, getName(), getPointerLevel());
    }

    @Override
    public YVariableRef withPointerLevel(int level) {
        return new YVariableRef(codeLocation(), getKind(), getName(), level);
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
        if (!(o instanceof YVariableRef)) { return false; }
        if (!super.equals(o)) { return false; }
        YVariableRef that = (YVariableRef) o;
        return getPointerLevel() == that.getPointerLevel() &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getPointerLevel());
    }
}
