package mousquetaires.languages.internalrepr.expressions;

public abstract class InternalConstant extends InternalExpression {

    public enum Type {
        Boolean,
        Int32,
    }

    protected final Object value;
    protected final Type type;

    public InternalConstant(String originalExpression, Object value, Type type) {
        super(originalExpression);
        this.value = value;
        this.type = type;
    }

    //public ArithExpr toZ3(MapSSA map, Context ctx) {
    //    return ctx.mkInt(bitness);
    //}
    //
    //public Set<Register> getRegs() {
    //    return new HashSet<Register>();
    //}
}
