package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.utils.exceptions.NotSupportedException;


public abstract class YUnlabeledStatement extends YStatement {

    @Override
    public YStatement withLabel(String newLabel) {
        throw new UnsupportedOperationException();
    }

}
