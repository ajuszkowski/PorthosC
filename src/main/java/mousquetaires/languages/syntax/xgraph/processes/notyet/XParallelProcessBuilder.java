package mousquetaires.languages.syntax.xrepr.processes.notyet;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xrepr.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xrepr.processes.XProcessBuilder;


public class XParallelProcessBuilder extends XProcessBuilder {

    private final ImmutableList.Builder<XBarrierEvent> barrierEvents;

    public XParallelProcessBuilder(String processId) {
        super(processId);
        // todo: initial capacity, load factor ...
        this.barrierEvents = new ImmutableList.Builder<>();
    }

    @Override
    public XParallelProcess build() {
        finishBuilding();
        return new XParallelProcess(this);
    }

    public ImmutableList<XBarrierEvent> buildBarrierEvents() {
        return barrierEvents.build();
    }
}
