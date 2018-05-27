package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.common.citation.Origin;

// TODO: according the C standard , not all statements can have label (if-then-else? while?)
public abstract class YUnlabeledStatement extends YStatement {

    public YUnlabeledStatement(Origin origin) {
        super(origin);
    }

    @Override
    public YStatement withLabel(String newLabel) {
        throw new UnsupportedOperationException();
    }
}
