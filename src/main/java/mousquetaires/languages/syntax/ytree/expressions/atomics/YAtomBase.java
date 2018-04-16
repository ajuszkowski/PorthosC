package mousquetaires.languages.syntax.ytree.expressions.atomics;

import java.util.Objects;


public abstract class YAtomBase implements YAtom {

    private final Kind kind;

    YAtomBase(Kind kind) {
        this.kind = kind;
    }

    @Override
    public Kind getKind() {
        return kind;
    }

    public boolean isGlobal() {
        return getKind() == Kind.Global;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof YAtomBase)) { return false; }
        YAtomBase yAtomBase = (YAtomBase) o;
        return getKind() == yAtomBase.getKind();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKind());
    }
}
