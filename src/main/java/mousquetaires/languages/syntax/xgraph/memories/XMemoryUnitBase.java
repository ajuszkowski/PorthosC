package mousquetaires.languages.syntax.xgraph.memories;


import mousquetaires.languages.common.Type;


abstract class XMemoryUnitBase implements XMemoryUnit {

    private final Type type;

    XMemoryUnitBase(Type type) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return type;
    }
}
