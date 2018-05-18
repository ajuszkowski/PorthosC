package mousquetaires.languages.syntax.xgraph.program;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowTree;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.*;


public abstract class XProgramBase <P extends FlowGraph<XEvent>>
        extends FlowTree<XEvent, P> {

    //private final XPreProcess prelude;
    //private final XPostProcess postlude;

    // memoised subsets
    private ImmutableSet<XEntryEvent> entryEvents;
    private ImmutableSet<XExitEvent> exitEvents;
    private ImmutableSet<XMemoryEvent> memoryEvents;
        private ImmutableSet<XInitialWriteEvent> initEvents;
        private ImmutableSet<XSharedMemoryEvent> sharedMemoryEvents;
            private ImmutableSet<XLoadMemoryEvent> loadMemoryEvents;
            private ImmutableSet<XStoreMemoryEvent> storeMemoryEvents;
    private ImmutableSet<XBarrierEvent> barrierEvents;


    XProgramBase(ImmutableList<P> processes) {
        //this.prelude = prelude;
        //this.postlude = postlude;
        super(processes);
    }

    public ImmutableList<P> getProcesses() {
        return getGraphs();
    }

    // TODO
    //public ImmutableList<P> getProcess(XProcessId processId) {
    //    for (P process : getProcesses()) {
    //
    //    }
    //}

    public ImmutableSet<XEntryEvent> getEntryEvents() {
        // TODO: just collect source() events here
        ImmutableSet.Builder<XEntryEvent> builder = new ImmutableSet.Builder<>();
        for (P process : getProcesses()) {
            builder.add((XEntryEvent) process.source()); //TODO: after merging XProcess && XUnrolledProcess, remove this cast
        }
        return builder.build();
    }

    public ImmutableSet<XExitEvent> getExitEvents() {
        // TODO: just collect sink() events here
        return exitEvents != null
                ? exitEvents
                : (exitEvents = getAllNodesExceptSource(XExitEvent.class));
    }

    public ImmutableSet<XMemoryEvent> getMemoryEvents() {
        return memoryEvents != null
                ? memoryEvents
                : (memoryEvents = getAllNodesExceptSource(XMemoryEvent.class));
    }

    public ImmutableSet<XInitialWriteEvent> getInitEvents() {
        return initEvents != null
                ? initEvents
                : (initEvents = getAllNodesExceptSource(XInitialWriteEvent.class));
    }

    public ImmutableSet<XSharedMemoryEvent> getSharedMemoryEvents() {
        return sharedMemoryEvents != null
                ? sharedMemoryEvents
                : (sharedMemoryEvents = getAllNodesExceptSource(XSharedMemoryEvent.class));
    }

    public ImmutableSet<XLoadMemoryEvent> getLoadMemoryEvents() {
        return loadMemoryEvents != null
                ? loadMemoryEvents
                : (loadMemoryEvents = getAllNodesExceptSource(XLoadMemoryEvent.class));
    }

    public ImmutableSet<XStoreMemoryEvent> getStoreMemoryEvents() {
        return storeMemoryEvents != null
                ? storeMemoryEvents
                : (storeMemoryEvents = getAllNodesExceptSource(XStoreMemoryEvent.class));
    }

    public ImmutableSet<XBarrierEvent> getBarrierEvents() {
        return barrierEvents != null
                ? barrierEvents
                : (barrierEvents = getAllNodesExceptSource(XBarrierEvent.class));
    }

    public int size() {
        int result = 0;
        for (P process : getProcesses()) {
            result += process.size();
        }
        return result;
    }

    private <S extends XEvent> ImmutableSet<S> getAllNodesExceptSource(Class<S> type) {
        ImmutableSet.Builder<S> builder = new ImmutableSet.Builder<>();
        for (P process : getProcesses()) {
            for (XEvent event : process.getAllNodesExceptSource()) {
                if (type.isInstance(event)) {
                    builder.add(type.cast(event));
                }
            }
        }
        return builder.build();
    }
}