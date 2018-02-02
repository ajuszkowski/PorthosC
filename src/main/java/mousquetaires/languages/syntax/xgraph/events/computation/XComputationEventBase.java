package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;


public abstract class XComputationEventBase extends XEventBase implements XComputationEvent {

    private final Bitness bitness;

    public XComputationEventBase(XEventInfo info, Bitness bitness) {
        super(info);
        this.bitness = bitness;
    }

    @Override
    public Bitness getBitness() {
        return bitness;
    }
}
