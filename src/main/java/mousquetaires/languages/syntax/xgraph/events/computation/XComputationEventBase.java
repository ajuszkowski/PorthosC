package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.common.Bitness;


// TODO: remove nullary computation event, inherit the XLocalMemoryUnit from XEventBase and XComputationEvent (because it's read from registry)
abstract class XComputationEventBase extends XEventBase implements XComputationEvent {

    private final XOperator operator;
    private final Bitness bitness;

    XComputationEventBase(XEventInfo info, Bitness bitness, XOperator operator) {
        super(info);
        this.bitness = bitness;
        this.operator = operator;
    }

    protected XOperator getOperator() {
        return operator;
    }

    @Override
    public Bitness getBitness() {
        return bitness;
    }
}
