package mousquetaires.languages.syntax.xgraph.memories;


import mousquetaires.languages.common.XType;


abstract class XMemoryUnitBase implements XMemoryUnit {

    private final XType type;

    XMemoryUnitBase(XType type) {
        this.type = type;
    }

    @Override
    public XType getType() {
        return type;
    }
}
