package mousquetaires.languages.syntax.xgraph.memories;

import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;


public class XLocation extends XMemoryUnitBase implements XSharedMemoryUnit {

    XLocation(String name, Bitness bitness) {
        super("loc_" + name, bitness);
    }

    @Override
    public <T> T accept(XMemoryUnitVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
