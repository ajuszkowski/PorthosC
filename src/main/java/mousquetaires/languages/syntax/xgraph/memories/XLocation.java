package mousquetaires.languages.syntax.xgraph.memories;

import mousquetaires.languages.visitors.xgraph.XgraphVisitor;


public class XLocation extends XMemoryUnitBase implements XSharedMemoryUnit {

    XLocation(String name, Bitness bitness) {
        super("l_" + name, bitness);
    }

    @Override
    public <T> T accept(XgraphVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
