package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;


// TODO: remove nullary computation event, inherit the XLocalMemoryUnit from XEventBase and XComputationEvent (because it's read from registry)
abstract class XComputationEventBase extends XEventBase implements XComputationEvent {

    private final XOperator operator;
    private final Type type;

    XComputationEventBase(XEventInfo info, Type type, XOperator operator) {
        super(info);
        this.type = type;
        this.operator = operator;
    }

    protected XOperator getOperator() {
        return operator;
    }

    @Override
    public Type getType() {
        return type;
    }
}
