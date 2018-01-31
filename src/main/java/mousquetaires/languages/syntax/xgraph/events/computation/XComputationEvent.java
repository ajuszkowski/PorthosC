package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;


public abstract class XComputationEvent extends XEventBase implements XLocalMemoryUnit {

    private final Bitness bitness;

    public XComputationEvent(XEventInfo info, Bitness bitness) {
        super(info);
        this.bitness = bitness;
    }

    @Override
    public Bitness getBitness() {
        return bitness;
    }
}
