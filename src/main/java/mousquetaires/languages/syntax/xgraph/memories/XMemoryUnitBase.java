package mousquetaires.languages.syntax.xgraph.memories;


import mousquetaires.languages.common.Bitness;


abstract class XMemoryUnitBase implements XMemoryUnit {

    private final Bitness bitness;

    XMemoryUnitBase(Bitness bitness) {
        this.bitness = bitness;
    }

    @Override
    public Bitness getBitness() {
        return bitness;
    }
}
