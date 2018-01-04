package mousquetaires.execution.events;

import mousquetaires.execution.memory.SharedLocation;
import mousquetaires.execution.memory.RegistryLocation;


/** Write event from registry ({@link mousquetaires.execution.memory.RegistryLocation})
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
