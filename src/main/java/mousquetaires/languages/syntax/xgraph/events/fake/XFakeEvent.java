package mousquetaires.languages.syntax.xgraph.events.fake;

import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;

import java.util.Objects;


public abstract class XFakeEvent extends XEventBase {
    private final int uniqueFakeEventId;

    XFakeEvent(XEventInfo info, int referenceId) {
        this(info, generateUniqueId(), referenceId);
    }

    XFakeEvent(XEventInfo info, int fakeEventId, int referenceId) {
        super(info, referenceId);
        this.uniqueFakeEventId = fakeEventId;
    }

    protected int getFakeEventId() {
        return uniqueFakeEventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XFakeEvent)) return false;
        if (!super.equals(o)) return false;
        XFakeEvent that = (XFakeEvent) o;
        return uniqueFakeEventId == that.uniqueFakeEventId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), uniqueFakeEventId);
    }

    private static int currentUniqueIndex = 1;
    private static int generateUniqueId() {
        return ++currentUniqueIndex;
    }
}
