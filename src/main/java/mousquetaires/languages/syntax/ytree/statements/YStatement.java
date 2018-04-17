package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.YEntity;


public abstract class YStatement implements YEntity {  // TODO: implement all as YJumpStatement
    private final CodeLocation location;
    private final String label;

    protected YStatement(CodeLocation location) {
        this(location, "_");
    }

    protected YStatement(CodeLocation location, String label) {
        this.location = location;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public CodeLocation codeLocation() {
        return location;
    }

    public abstract YStatement withLabel(String newLabel);

    private static int id = 1;
    protected static String newLabel() {
        return "__stmt" + id++;
    }
}
