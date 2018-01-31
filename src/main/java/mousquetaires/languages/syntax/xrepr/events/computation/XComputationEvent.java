package mousquetaires.languages.syntax.xrepr.events.computation;

import mousquetaires.languages.syntax.xrepr.events.memory.XLocalEvent;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;


public abstract class XComputationEvent extends XLocalMemoryUnit implements XLocalEvent {
    private final XEventInfo info;

    XComputationEvent(Bitness bitness, XEventInfo info) {
        super("comp" + info.getStamp(), bitness);
        this.info = info;
    }

    @Override
    public XEventInfo getInfo() {
        return info;
    }
}
