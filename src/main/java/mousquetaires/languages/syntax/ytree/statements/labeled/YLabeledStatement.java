package mousquetaires.languages.syntax.ytree.statements.labeled;

import mousquetaires.languages.syntax.ytree.statements.YStatement;


public abstract class YLabeledStatement extends YStatement {
    private final String label;

    protected YLabeledStatement(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public abstract YLabeledStatement withLabel(String newLabel);
}
