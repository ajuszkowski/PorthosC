package mousquetaires.languages.internal.expressions;


// old BConst or BooleanConst
public class BooleanConstantExpression extends ConstantExpression {
    public BooleanConstantExpression(String originalExpression, Object value) {
        // todo: conovert to true/false?
        super(originalExpression, value, Type.Boolean);
    }


    //public BoolExpr toZ3(MapSSA map, Context ctx) {
    //    if(value) {
    //        return ctx.mkTrue();
    //    }
    //    else {
    //        return ctx.mkFalse();
    //    }
    //}
}
