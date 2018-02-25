package mousquetaires.languages.syntax.xgraph.events.fakes;

import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
import mousquetaires.languages.visitors.xgraph.XgraphVisitor;
import mousquetaires.utils.exceptions.NotSupportedException;


public class XFakeComputationEvent extends XFakeEvent implements XComputationEvent {

    public XFakeComputationEvent(XEventInfo info) {
        super(info);
    }

    @Override
    public String getName() {
        return "fakecomp_" + getUniqueId();
    }

    @Override
    public <T> T accept(XgraphVisitor<T> visitor) {
        throw new NotSupportedException();
    }

    @Override
    public XMemoryUnit.Bitness getBitness() {
        throw new NotSupportedException();
    }
}
