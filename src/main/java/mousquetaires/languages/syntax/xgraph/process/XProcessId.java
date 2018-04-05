package mousquetaires.languages.syntax.xgraph.process;

public class XProcessId {
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
}
