package mousquetaires.languages.syntax.xgraph.process;

import java.util.Objects;


public class XProcessId {

    public static final XProcessId PreludeProcessId = new XProcessId("<>prelude<>");
    public static final XProcessId PostludeProcessId = new XProcessId("<>postlude<>");

    private final String value;

    public XProcessId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof XProcessId)) { return false; }
        XProcessId that = (XProcessId) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
