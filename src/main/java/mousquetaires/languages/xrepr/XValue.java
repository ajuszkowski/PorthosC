package mousquetaires.languages.xrepr;

import mousquetaires.languages.common.types.YXType;

import java.util.Objects;


public class XValue implements XEntity {
    public final YXType type;
    public final Object value;

    public XValue(YXType type, Object value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return value + ":" + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XValue)) return false;
        XValue xValue = (XValue) o;
        return Objects.equals(type, xValue.type) &&
                Objects.equals(value, xValue.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
