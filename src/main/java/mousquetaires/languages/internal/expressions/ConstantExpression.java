package mousquetaires.languages.internal.expressions;

public abstract class ConstantExpression extends Expression {

    public enum Type {
        Boolean,
        Int32,
    }

    protected final Object value;
    protected final Type type;

    public ConstantExpression(String originalExpression, Object value, Type type) {
        super(originalExpression);
        this.value = value;
        this.type = type;
    }

    //public ArithExpr toZ3(MapSSA map, Context ctx) {
    //    return ctx.mkInt(value);
    //}
    //
    //public Set<Register> getRegs() {
    //    return new HashSet<Register>();
    //}
}
