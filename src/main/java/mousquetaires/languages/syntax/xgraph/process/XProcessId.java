package mousquetaires.languages.syntax.xgraph.process;

public class XProcessId {

    public static final XProcessId preludeProcessId = new XProcessId("<>prelude<>");
    public static final XProcessId postludeProcessId = new XProcessId("<>postlude<>");

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
