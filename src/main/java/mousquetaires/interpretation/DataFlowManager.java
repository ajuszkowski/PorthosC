package mousquetaires.interpretation;

import mousquetaires.languages.xrepr.events.XReadEvent;
import mousquetaires.languages.xrepr.events.XWriteEvent;
import mousquetaires.languages.xrepr.memory.XLocation;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * This class is responsible for keeping information on data-flow of the program.
 * It stores read and write events, and keeps track of ordering between them (read-from 'rf' relations)
 */
class DataFlowManager {
    // TODO: to use hashmapes, set up hashes properly!!!

    private final static Map<XLocation, XReadEvent> readEvents = new HashMap<>();

    // See: La thèse de Jade Alglave, 3.2.2 Write Serialisation:
    // "We assume all values written to a given location l to be serialised, following a coherence order."
    private final static Map<XLocation, List<XWriteEvent>> writeSerialisation = new HashMap<>();

    private final static Map<XWriteEvent, XReadEvent> readFromRelations = new HashMap<>();

    public void registerReadEvent(XReadEvent event) {
        //readEvents.add(event);
        // TODO: here, add new relation from last write
        throw new NotImplementedException();
    }

    public void registerWriteEvent(XWriteEvent writeEvent) {
        XLocation destinationLocation = writeEvent.destination;
        List<XWriteEvent> previousEvents = writeSerialisation.get(destinationLocation);
        if (previousEvents == null) {  // first write to that location
            previousEvents = new LinkedList<>();
            previousEvents.add(writeEvent);
            writeSerialisation.put(destinationLocation, previousEvents);
        }
        else {
            previousEvents.add(writeEvent);
        }
    }
}
