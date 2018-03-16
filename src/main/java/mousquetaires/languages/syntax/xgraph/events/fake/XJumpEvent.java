package mousquetaires.languages.syntax.xgraph.events.fake;

import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;

import java.util.Objects;


public final class XJumpEvent extends XFakeEvent {

    private final int uniqueJumpIndex;

    public XJumpEvent(XEventInfo info) {
        super(info);
        this.uniqueJumpIndex = generateUniqueIndex();
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "[" + getLabel() + "]";
    }

    @Override
    public String getLabel() {
        return super.getLabel() + "_jump" + getUniqueJumpIndex();
    }

    public int getUniqueJumpIndex() {
        return uniqueJumpIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XJumpEvent)) return false;
        if (!super.equals(o)) return false;
        XJumpEvent that = (XJumpEvent) o;
        return uniqueJumpIndex == that.uniqueJumpIndex;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), uniqueJumpIndex);
    }

    private static int uniqueIndex = 1;
    private static int generateUniqueIndex() {
        return ++uniqueIndex;
    }

}
