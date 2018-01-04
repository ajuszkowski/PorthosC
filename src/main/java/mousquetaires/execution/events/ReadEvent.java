package mousquetaires.execution.events;

import mousquetaires.execution.memory.SharedLocation;

/** Read event from shared variable ({@link SharedLocation}) */
public class ReadEvent extends MemoryEvent {

    public final SharedLocation source;

    // todo: value - ?

    public ReadEvent(EventInfo info, SharedLocation source) {
        super(info);
        this.source = source;
    }

    @Override
    public String toString() {
        return "r(" + source + ')';
    }
}
