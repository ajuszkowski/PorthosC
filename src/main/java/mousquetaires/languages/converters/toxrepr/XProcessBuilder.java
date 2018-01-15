package mousquetaires.languages.converters.toxrepr;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.syntax.xrepr.events.XBarrierEvent;
import mousquetaires.languages.syntax.xrepr.XControlFlowEvent;
import mousquetaires.languages.syntax.xrepr.events.memory.XMemoryEvent;
import mousquetaires.languages.syntax.xrepr.XProcess;
import mousquetaires.utils.patterns.Builder;


public class XProcessBuilder extends Builder<XProcess> {

    private final String name;
    private final ImmutableSet.Builder<XMemoryEvent> memoryEvents;
    private final ImmutableSet.Builder<XBarrierEvent> barrierEvents;
    private final ImmutableSet.Builder<XControlFlowEvent> controlFlowEvents;


    // todo: relations

    XProcessBuilder(String name) {
        this.name = name;
        memoryEvents = new ImmutableSet.Builder<>(); // todo: initial capacity, load factor ...
        controlFlowEvents = new ImmutableSet.Builder<>();
        barrierEvents = new ImmutableSet.Builder<>();
    }

    @Override
    public XProcess build() {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        setBuilt();
        return new XProcess(name, memoryEvents.build(), barrierEvents.build(), controlFlowEvents.build());
    }

    public void addMemoryEvent(XMemoryEvent event) {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        memoryEvents.add(event);
    }

    public void addCallEvent(XControlFlowEvent event) {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        controlFlowEvents.add(event);
    }

    public void addBarrierEvent(XBarrierEvent event) {
        if (isBuilt()) {
            throw new RuntimeException(getAlreadyFinishedMessage());
        }
        barrierEvents.add(event);
    }

    public String getName() {
        return name;
    }
}
