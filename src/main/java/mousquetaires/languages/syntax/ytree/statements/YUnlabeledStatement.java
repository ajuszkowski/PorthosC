package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.common.citation.CodeLocation;

// TODO: according the C standard , not all statements can have label (if-then-else? while?)
public abstract class YUnlabeledStatement extends YStatement {

    public YUnlabeledStatement(CodeLocation location) {
        super(location);
    }

    @Override
    public YStatement withLabel(String newLabel) {
        throw new UnsupportedOperationException();
    }
}
