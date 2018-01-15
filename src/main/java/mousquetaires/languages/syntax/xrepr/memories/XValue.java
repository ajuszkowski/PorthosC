package mousquetaires.languages.syntax.xrepr.memories;

import mousquetaires.languages.types.YXType;

import java.util.Objects;


/** Shortcut for working with constant values as with values stored in local memoryevents (registers).
 * For example, the code 'r1 <- 1' is syntactic sugar for 'r1 <- m1' where the memoryevents location 'm1'
 * contains value '1', though it does not need to be considered in weak memoryevents model, as there are
 * no writes to that location, therefore we model it as a local storage. */
public class XValue extends XLocalMemoryUnit {

    public final Object value;

    public XValue(Object value, YXType type) {
        super("const", type);
        this.value = value;
    }

    @Override
    public String toString() {
        return "$" + value + ":" + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XValue)) return false;
        if (!super.equals(o)) return false;
        XValue xValue = (XValue) o;
        return Objects.equals(value, xValue.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), value);
    }
}
