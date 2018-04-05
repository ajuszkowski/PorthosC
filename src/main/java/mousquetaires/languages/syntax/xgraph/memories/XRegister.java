package mousquetaires.languages.syntax.xgraph.memories;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;


public final class XRegister extends XLvalueMemoryUnitBase implements XLocalLvalueMemoryUnit {

    private final XProcessId processId;

    public XRegister(String name, Type type, XProcessId processId) {
        super(name, type);
        this.processId = processId;
    }

    @Override
    public XProcessId getProcessId() {
        return processId;
    }

    @Override
    public <T> T accept(XMemoryUnitVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
