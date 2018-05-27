package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.common.citation.Origin;


public abstract class YAtomBase implements YAtom {

    private final Origin location;
    private final Kind kind;
    private final int pointerLevel;

    YAtomBase(Origin location, Kind kind) {
        this(location, kind, 0);
    }

    YAtomBase(Origin location, Kind kind, int pointerLevel) {
        this.location = location;
        this.kind = kind;
        this.pointerLevel = pointerLevel;
    }

    @Override
    public Kind getKind() {
        return kind;
    }

    @Override
    public Origin codeLocation() {
        return location;
    }

    public boolean isGlobal() {
        return getKind() == Kind.Global;
    }

    public int getPointerLevel() {
        return pointerLevel;
    }
}
