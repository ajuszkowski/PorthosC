package mousquetaires.languages.syntax.ytree.statements.jumps;

public class YJumpLabel {
    private final String value;

    public YJumpLabel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
