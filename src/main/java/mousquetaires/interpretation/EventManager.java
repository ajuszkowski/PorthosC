package mousquetaires.interpretation;

import mousquetaires.execution.events.ReadEvent;
import mousquetaires.execution.events.WriteEvent;

import java.util.ArrayList;
import java.util.List;


class EventManager {
    private final static List<ReadEvent> readEvents = new ArrayList<>();
    private final static List<WriteEvent> writeEvents = new ArrayList<>();

    public void registerReadEvent(ReadEvent event) {
        readEvents.add(event);
    }

    public void registerWriteEvent(WriteEvent event) {
        writeEvents.add(event);
    }
}
