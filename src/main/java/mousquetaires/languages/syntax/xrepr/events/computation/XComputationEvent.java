package mousquetaires.languages.syntax.xrepr.events.computation;

import mousquetaires.languages.syntax.xrepr.events.XLocalEvent;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;
import mousquetaires.types.ZType;


public abstract class XComputationEvent extends XLocalMemoryUnit implements XLocalEvent {

    public final XEventInfo info;

    XComputationEvent(XEventInfo info, ZType type) {
        super("(compute)", type);
        this.info = info;
    }

    @Override
    public XEventInfo getInfo() {
        return info;
    }
}
