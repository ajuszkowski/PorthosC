package mousquetaires.languages.syntax.xgraph.events.memory;

import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;


/**
 * Event of writing from one local memory to another (e.g. from one register to sth).
 */
public class XRegisterMemoryEvent extends XMemoryEventBase implements XLocalMemoryEvent {

    public XRegisterMemoryEvent(XEventInfo info, XLocalMemoryUnit destination, XLocalMemoryUnit source) {
        super(info, destination, source);
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
    public <T> T accept(XgraphVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return getDestination() + " := " + getSource();
    }

    @Override
    public String getUniqueId() {
        return super.getUniqueId() + "_regreg";
    }
}
