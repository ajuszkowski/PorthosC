package mousquetaires.languages.syntax.xrepr.memories;

import mousquetaires.languages.syntax.xrepr.types.XType;

import java.util.Objects;


/** Shortcut for working with constant values as with values stored in local memoryevents (registers).
 * For example, the code 'r1 <- 1' is syntactic sugar for 'r1 <- m1' where the memoryevents location 'm1'
 * contains value '1', though it does not need to be considered in weak memoryevents model, as there are
 * no writes to that location, therefore we model it as a local storage. */
public class XValue extends XLocalMemoryUnit {

    public final Object value;

    public XValue(Object value, XType type) {
        super("(const)", type);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "$" + getValue() + ":" + getType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XValue)) return false;
        if (!super.equals(o)) return false;
        XValue xValue = (XValue) o;
        return Objects.equals(getValue(), xValue.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getValue());
    }
}
