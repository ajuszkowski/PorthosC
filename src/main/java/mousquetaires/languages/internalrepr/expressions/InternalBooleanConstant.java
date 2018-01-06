package mousquetaires.languages.internalrepr.expressions;


// old BConst or BooleanConst
public class InternalBooleanConstant extends InternalConstant {
    public InternalBooleanConstant(Object value) {
        // todo: convert to true/false?
        super(value, Type.Boolean);
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
