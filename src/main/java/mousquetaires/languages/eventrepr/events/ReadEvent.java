package mousquetaires.languages.eventrepr.events;

import mousquetaires.languages.eventrepr.memory.SharedLocation;

/** Read event from shared assignee ({@link SharedLocation}) */
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
