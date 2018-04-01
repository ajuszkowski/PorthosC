package mousquetaires.languages.syntax.zformula;

import mousquetaires.languages.common.Bitness;


abstract class ZAtomBase implements ZAtom {
    private final Bitness bitness;

    public ZAtomBase(Bitness bitness) {
        this.bitness = bitness;
    }

    @Override
    public Bitness getBitness() {
        return bitness;
    }
}
