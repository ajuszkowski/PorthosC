package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.YEntity;


public abstract class YStatement implements YEntity {  // TODO: implement all as YJumpStatement
    private final Origin origin;
    private final String label;

    protected YStatement(Origin origin) {
        this(origin, newLabel());
    }

    protected YStatement(Origin origin, String label) {
        this.origin = origin;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public Origin origin() {
        return origin;
    }

    // the label in terms of C (like goto label)
    public abstract YStatement withLabel(String newLabel);

    private static int id = 1;
    protected static String newLabel() {
        return "__stmt" + id++;
    }
}
