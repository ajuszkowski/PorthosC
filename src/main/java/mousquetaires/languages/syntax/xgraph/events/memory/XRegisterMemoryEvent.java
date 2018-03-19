package mousquetaires.languages.syntax.xgraph.events.memory;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


/**
 * Event of writing from one local memory to another (e.g. from one register to sth).
 */
public final class XRegisterMemoryEvent extends XMemoryEventBase implements XLocalMemoryEvent {

    public XRegisterMemoryEvent(XEventInfo info, XLocalMemoryUnit destination, XLocalMemoryUnit source) {
        this(info, destination, source, NON_REFERENCE_ID);
    }

    private XRegisterMemoryEvent(XEventInfo info, XLocalMemoryUnit destination, XLocalMemoryUnit source, int referenceId) {
        super(info, destination, source, referenceId);
        if (destination instanceof XConstant) {
            throw new IllegalArgumentException("Memory event with assignment to " +
                    XConstant.class.getName() + " is not allowed");
        }
    }

    @Override
    public XLocalMemoryUnit getDestination() {
        return (XLocalMemoryUnit) super.getDestination();
    }

    @Override
    public XLocalMemoryUnit getSource() {
        return (XLocalMemoryUnit) super.getSource();
    }

    @Override
    public XEvent asReference(int referenceId) {
        return new XRegisterMemoryEvent(getInfo(), getDestination(), getSource(), referenceId);
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }


    @Override
    public String toString() {
        return wrapWithBracketsAndReferenceId(getDestination() + " := " + getSource());
    }

    @Override
    public String getLabel() {
        return super.getLabel() + "_regreg";
    }
}
