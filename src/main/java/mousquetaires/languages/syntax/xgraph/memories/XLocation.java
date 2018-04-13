package mousquetaires.languages.syntax.xgraph.memories;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;


public final class XLocation extends XLvalueMemoryUnitBase implements XSharedLvalueMemoryUnit {

    public XLocation(String name, Type type, boolean isResolved) {
        super(name, type, isResolved);
    }

    @Override
    public <T> T accept(XMemoryUnitVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
