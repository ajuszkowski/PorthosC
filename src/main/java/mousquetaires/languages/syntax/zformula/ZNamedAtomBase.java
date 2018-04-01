package mousquetaires.languages.syntax.zformula;

import mousquetaires.languages.common.Bitness;


abstract class ZNamedAtomBase extends ZAtomBase implements ZNamedAtom {

    private final String name;

    ZNamedAtomBase(String name, Bitness bitness) {
        super(bitness);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
