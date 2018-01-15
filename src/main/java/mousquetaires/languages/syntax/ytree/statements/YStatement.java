package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.syntax.ytree.YEntity;


public abstract class YStatement implements YEntity {

    public final String label;

    protected YStatement(String label) {
        this.label = label;
    }

    public abstract YStatement withLabel(String newLabel);
}
