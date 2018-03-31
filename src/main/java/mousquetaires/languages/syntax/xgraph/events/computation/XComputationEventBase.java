package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.utils.exceptions.NotImplementedException;


// TODO: remove nullary computation event, inherit the XLocalMemoryUnit from XEventBase and XComputationEvent (because it's read from registry)
public abstract class XComputationEventBase extends XEventBase implements XComputationEvent {

    private final Bitness bitness;

    XComputationEventBase(XEventInfo info, Bitness bitness, int referenceId) {
        super(info, referenceId);
        this.bitness = bitness;
    }

    @Override
    public String getName() {
        throw new NotImplementedException(); //??
    }

    @Override
    public Bitness getBitness() {
        return bitness;
    }
}
