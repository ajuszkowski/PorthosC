package mousquetaires.languages.internalrepr.expressions;


// old BConst or BooleanConst
public class InternalBooleanConstant extends InternalConstant {
    public InternalBooleanConstant(String originalExpression, Object value) {
        // todo: conovert to true/false?
        super(originalExpression, value, Type.Boolean);
    }


    //public BoolExpr toZ3(MapSSA map, Context ctx) {
    //    if(bitness) {
    //        return ctx.mkTrue();
    //    }
    //    else {
    //        return ctx.mkFalse();
    //    }
    //}
}
