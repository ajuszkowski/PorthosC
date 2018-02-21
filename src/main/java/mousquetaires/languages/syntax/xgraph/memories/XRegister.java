package mousquetaires.languages.syntax.xgraph.memories;

import mousquetaires.languages.visitors.xgraph.XgraphVisitor;


public class XRegister extends XMemoryUnitBase implements XLocalMemoryUnit {

    protected XRegister(String name, Bitness bitness) {
        super("r_" + name, bitness);
    }

    @Override
    public <T> T accept(XgraphVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
