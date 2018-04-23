package mousquetaires.languages.syntax.xgraph.events;

import mousquetaires.languages.syntax.xgraph.process.XProcessId;

import java.util.Objects;


public abstract class XEventBase implements XEvent {

    private final int refId;
    private final XEventInfo info;

    public XEventBase(int refId, XEventInfo info) {
        this.refId = refId;
        this.info = info;
    }

    @Override
    public int getUniqueId() {
        return getInfo().getEventId();
    }

    @Override
    public int getRefId() {
        return refId;
    }

    @Override
    public XEventInfo getInfo() {
        return info;
    }

    @Override
    public String getName() {
        return "e_" + hashCode();
    }

    @Override
    public XProcessId getProcessId() {
        return getInfo().getProcessId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof XEventBase)) { return false; }
        XEventBase that = (XEventBase) o;
        return getRefId() == that.getRefId() &&
                Objects.equals(getInfo(), that.getInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRefId(), getInfo());
    }

    protected String wrapWithBracketsAndDepth(String message) {
        String refIdSuffix = (refId == NOT_UNROLLED_REF_ID) ? "" : "," + refId;
        return "[" + message + refIdSuffix + "]";
    }
}
