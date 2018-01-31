package mousquetaires.languages.syntax.xrepr.processes.notyet;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xrepr.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xrepr.processes.XProcess;


public class XParallelProcess extends XProcess {

    private final ImmutableList<XBarrierEvent> barrierEvents;

    XParallelProcess(XParallelProcessBuilder builder) {
        super(builder);
        this.barrierEvents = builder.buildBarrierEvents();
    }

    public ImmutableList<XBarrierEvent> getBarrierEvents() {
        return barrierEvents;
    }
}
