package mousquetaires.languages.eventrepr.events;

import mousquetaires.languages.ytree.YEntity;

import java.util.Objects;


public abstract class Event implements YEntity {
    public final EventInfo info;

    Event(EventInfo info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(info, event.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info);
    }
}
