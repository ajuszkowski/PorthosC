package mousquetaires.languages.syntax.xgraph.events.memory;

import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XLocalLvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


/**
 * XEvent of writing from one local memory to another (e.g. from one register to sth).
 */
public final class XRegisterMemoryEvent extends XMemoryEventBase implements XLocalMemoryEvent {

    public XRegisterMemoryEvent(XEventInfo info, XLocalLvalueMemoryUnit destination, XLocalMemoryUnit source) {
        super(info, destination, source);
    }

    @Override
    public XLocalLvalueMemoryUnit getDestination() {
        return (XLocalLvalueMemoryUnit) super.getDestination();
    }

    @Override
    public XLocalMemoryUnit getSource() {
        return (XLocalMemoryUnit) super.getSource();
    }

    @Override
    public XRegisterMemoryEvent withInfo(XEventInfo newInfo) {
        return new XRegisterMemoryEvent(newInfo, getDestination(), getSource());
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        //return wrapWithBracketsAndDepth(getDestination() + " := " + getSource());
        return "LOCALMEM_" + getDestination() + "_" + getSource();
    }
}
