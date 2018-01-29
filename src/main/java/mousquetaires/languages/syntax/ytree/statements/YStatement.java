package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.syntax.ytree.YEntity;


public abstract class YStatement implements YEntity {
    private final String label;

    protected YStatement() {
        this.label = "";
    }

    protected YStatement(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public abstract YStatement withLabel(String newLabel);

    private static int id = 1;
    protected static String newLabel() {
        return "__stmt" + id++;
    }
}
