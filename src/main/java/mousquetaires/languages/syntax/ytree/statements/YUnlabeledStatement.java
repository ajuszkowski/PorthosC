package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.common.citation.CodeLocation;


public abstract class YUnlabeledStatement extends YStatement {

    public YUnlabeledStatement(CodeLocation location) {
        super(location);
    }

    @Override
    public YStatement withLabel(String newLabel) {
        throw new UnsupportedOperationException();
    }
}
