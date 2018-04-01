package mousquetaires.languages.syntax.zformula;


public enum ZUnaryOperator implements ZOperator {
    BitNegation,
    NoOperation,
    ;

    public ZUnaryOperation create(ZAtom operand) {
        return new ZUnaryOperation(this, operand);
    }

    @Override
    public String toString() {
        switch (this) {
            case BitNegation: return "~";
            case NoOperation: return "_";
            default:
                throw new IllegalArgumentException(this.name());
        }
    }
}
