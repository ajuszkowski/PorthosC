package mousquetaires.languages.eventrepr.events;

import mousquetaires.languages.eventrepr.memory.SharedLocation;
import mousquetaires.languages.eventrepr.memory.RegistryLocation;


/** Write event from registry ({@link mousquetaires.languages.eventrepr.memory.RegistryLocation})
 * to shared variable ({@link SharedLocation})
 */
public class WriteEvent extends MemoryEvent {

    public final RegistryLocation source;
    public final SharedLocation destination;

    public WriteEvent(EventInfo info, RegistryLocation source, SharedLocation destination) {
        super(info);
        this.source = source;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "w(" + source +
                ", " + destination +
                ')';
    }
}
