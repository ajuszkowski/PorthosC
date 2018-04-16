package mousquetaires.languages.syntax.ytree.expressions.atomics;

public abstract class YAtomBase implements YAtom {

    private final Kind kind;
    private final int pointerLevel;

    YAtomBase(Kind kind) {
        this(kind, 0);
    }

    YAtomBase(Kind kind, int pointerLevel) {
        this.kind = kind;
        this.pointerLevel = pointerLevel;
    }

    @Override
    public Kind getKind() {
        return kind;
    }

    public boolean isGlobal() {
        return getKind() == Kind.Global;
    }

    public int getPointerLevel() {
        return pointerLevel;
    }
}
