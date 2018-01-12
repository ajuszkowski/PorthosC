package mousquetaires.languages.xrepr.events;

import mousquetaires.languages.xrepr.XEntity;

import java.util.Objects;


public abstract class XEvent implements XEntity {
    public final XEventInfo info;

    XEvent(XEventInfo info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof XEvent)) {
            return false;
        }
        XEvent event = (XEvent) o;
        return Objects.equals(info, event.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info);
    }
}
