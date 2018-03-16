package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;


public abstract class XComputationEventBase extends XEventBase implements XComputationEvent {

    private final Bitness bitness;

    XComputationEventBase(XEventInfo info, Bitness bitness) {
        super(info);
        this.bitness = bitness;
    }

    @Override
    public String getLabel() {
        return super.getLabel() + "_comp";
    }

    @Override
    public String getName() {
        return getLabel();
    }

    @Override
    public Bitness getBitness() {
        return bitness;
    }
}
