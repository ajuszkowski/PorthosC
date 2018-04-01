package mousquetaires.languages.syntax.xgraph.events;

import java.util.Objects;


public abstract class XEventBase implements XEvent {

    private final XEventInfo info;

    public XEventBase(XEventInfo info) {
        this.info = info;
    }

    @Override
    public XEventInfo getInfo() {
        return info;
    }

    @Override
    public String getName() {
        return "e_" + getInfo().getText();
    }

    //TODO: remove this debug method
    protected String wrapWithBracketsAndDepth(String message) {
        return "[" + message + ", " + getInfo().getUnrollingDepth() + "]";
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof XEventBase)) { return false; }
        XEventBase that = (XEventBase) o;
        return Objects.equals(getInfo(), that.getInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInfo());
    }
}
