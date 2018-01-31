package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.events.memory.XLocalEvent;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;


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
