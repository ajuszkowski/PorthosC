package mousquetaires.languages.syntax.xgraph.memories;


import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;


/** Shortcut for working with constant values as with values stored in local memoryevents (registers).
 * For example, the code 'r1 <- 1' is syntactic sugar for 'r1 <- m1' where the memoryevents location 'm1'
 * contains value '1', though it does not need to be considered in weak memoryevents model, as there are
 * no writes to that location, therefore we model it as a local storage. */
public class XConstant extends XRegister {

    private final Object value;

    // TODO: maybe add types (bit1, bit32

    XConstant(Object value, Bitness bitness) {
        super("const" + value/*.hashCode()//todo: uncomment after debugging*/, bitness);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public <T> T accept(XMemoryUnitVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
