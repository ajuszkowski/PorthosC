package mousquetaires.languages.syntax.xgraph.events.computation;

public enum XUnaryOperator implements XOperator {
    BitNegation,
    NoOperation,
    ;

    @Override
    public String toString() {
        switch (this) {
            case BitNegation:
                return "!";
            case NoOperation:
                return "_";
            default:
                throw new IllegalStateException(this.name());
        }
    }
}
