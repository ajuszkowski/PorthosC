package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.YEntity;


public abstract class YStatement implements YEntity {  // TODO: implement all as YJumpStatement
    private final Origin location;
    private final String label;

    protected YStatement(Origin location) {
        this(location, newLabel());
    }

    protected YStatement(Origin location, String label) {
        this.location = location;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public Origin codeLocation() {
        return location;
    }

    // the label in terms of C (like goto label)
    public abstract YStatement withLabel(String newLabel);

    private static int id = 1;
    protected static String newLabel() {
        return "__stmt" + id++;
    }
}
