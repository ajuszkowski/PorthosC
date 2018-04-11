package mousquetaires.languages.syntax.ytree.statements;

public abstract class YUnlabeledStatement extends YStatement {

    @Override
    public YStatement withLabel(String newLabel) {
        throw new UnsupportedOperationException();
    }

}
