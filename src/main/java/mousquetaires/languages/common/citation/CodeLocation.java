package mousquetaires.languages.common.citation;

public class CodeLocation {

    private final String file;
    private final int startIndex;
    private final int endIndex;

    public CodeLocation(String file, int startIndex, int endIndex) {
        this.file = file;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int start() {
        return startIndex;
    }

    public int end() {
        return endIndex;
    }

    public static final CodeLocation empty = new CodeLocation("", -1, -1);
}
