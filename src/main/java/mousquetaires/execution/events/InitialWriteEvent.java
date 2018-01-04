package mousquetaires.execution.events;

import mousquetaires.execution.memory.MemoryLocation;


/** Initial write event to any kind of memory location ({@link MemoryLocation}) */
public class InitialWriteEvent extends MemoryEvent {

    public final MemoryLocation source;

    // todo: value - ?

    public InitialWriteEvent(EventInfo info, MemoryLocation source) {
        super(info);
        this.source = source;
    }

    @Override
    public String toString() {
        return "iw" + source + ')';
    }
}
