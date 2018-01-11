package mousquetaires.interpretation.eventrepr;

import mousquetaires.languages.xrepr.events.XReadEvent;
import mousquetaires.languages.xrepr.events.XWriteEvent;

import java.util.ArrayList;
import java.util.List;


class EventManager {
    private final static List<XReadEvent> readEvents = new ArrayList<>();
    private final static List<XWriteEvent> writeEvents = new ArrayList<>();

    public void registerReadEvent(XReadEvent event) {
        readEvents.add(event);
    }

    public void registerWriteEvent(XWriteEvent event) {
        writeEvents.add(event);
    }
}
