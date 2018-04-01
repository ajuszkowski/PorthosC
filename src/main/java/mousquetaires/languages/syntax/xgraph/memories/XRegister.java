package mousquetaires.languages.syntax.xgraph.memories;

import mousquetaires.languages.common.Bitness;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;


public class XRegister extends XLvalueMemoryUnitBase implements XLocalMemoryUnit {

    protected XRegister(String name, Bitness bitness) {
        super("reg_" + name, bitness);
    }

    @Override
    public <T> T accept(XMemoryUnitVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
