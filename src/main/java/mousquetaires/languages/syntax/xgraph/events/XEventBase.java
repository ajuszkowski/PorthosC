package mousquetaires.languages.syntax.xgraph.events;


import java.util.Objects;


public abstract class XEventBase implements XEvent {

    private final XEventInfo info;
    private final int referenceId;

    protected XEventBase(XEventInfo info, int referenceId) {
        this.info = info;
        this.referenceId = referenceId;
    }

    @Override
    public XEventInfo getInfo() {
        return info;
    }

    @Override
    public int getReferenceId() {
        return referenceId;
    }

    @Override
    public String toString() {
        return wrapWithBracketsAndReferenceId(getInfo().getProcessId() + "_e" + hashCode()); //getInfo().getStamp();
    }

    // TODO: do we need to add referenceId to hashCode ?
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XEventBase)) return false;
        XEventBase that = (XEventBase) o;
        return Objects.equals(getInfo(), that.getInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInfo());
    }

    protected String wrapWithBracketsAndReferenceId(String line) {
        return"[" + (isReference() ? ", " + referenceId : "") + "]";
    }
}
