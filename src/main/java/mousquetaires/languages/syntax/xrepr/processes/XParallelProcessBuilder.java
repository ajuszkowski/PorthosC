package mousquetaires.languages.syntax.xrepr.processes;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.converters.toxrepr.XMemoryManager;
import mousquetaires.languages.syntax.xrepr.events.barrier.XBarrierEvent;


public class XParallelProcessBuilder extends XProcessBuilder {

    private final ImmutableList.Builder<XBarrierEvent> barrierEvents;

    public XParallelProcessBuilder(int processId, XMemoryManager memoryManager) {
        super(processId, memoryManager);
        // todo: initial capacity, load factor ...
        this.barrierEvents = new ImmutableList.Builder<>();
    }

    @Override
    public XParallelProcess build() {
        finish();
        return new XParallelProcess(this);
    }

    public ImmutableList<XBarrierEvent> buildBarrierEvents() {
        return barrierEvents.build();
    }
}
