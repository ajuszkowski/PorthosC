package mousquetaires.languages.internal.expressions;

public class BooleanExpression extends Expression {

    private Expression leftExpression;
    private Expression rightExpression;
    private OperatorKind operator;

    public BooleanExpression(String originalExpression,
                             BooleanExpression leftExpression,
                             OperatorKind operator,
                             BooleanExpression rightExpression) {
        super(originalExpression);
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    public enum OperatorKind {
        Plus,
        Minus,
        Multiply,
    }

    //public String toString() {
    //    return String.format("(%s %s %s)", leftExpression, operator, rightExpression);
    //}
    //
    //public BooleanExpression clone() {
    //    BooleanExpression newB1 = leftExpression.clone();
    //    BooleanExpression newB2 = rightExpression.clone();
    //    return new BooleanExpression(newB1, operator, newB2);
    //}

    //public BoolExpr toZ3(MapSSA map, Context ctx) {
    //    switch(operator) {
    //    case "and": return ctx.mkAnd(leftExpression.toZ3(map, ctx), rightExpression.toZ3(map, ctx));
    //    case "or": return ctx.mkOr(leftExpression.toZ3(map, ctx), rightExpression.toZ3(map, ctx));
    //    case "not": return ctx.mkNot(leftExpression.toZ3(map, ctx));
    //    }
    //    System.out.println(String.format("Check toz3() for %s", this));
    //    return null;
    //}

    // from Atom:
    //public BoolExpr toZ3(MapSSA map, Context ctx) {
    //    switch(op) {
    //        case "==": return ctx.mkEq(lhs.toZ3(map, ctx), rhs.toZ3(map, ctx));
    //        case "!=": return ctx.mkNot(ctx.mkEq(lhs.toZ3(map, ctx), rhs.toZ3(map, ctx)));
    //        case "<": return ctx.mkLt(lhs.toZ3(map, ctx), rhs.toZ3(map, ctx));
    //        case "<=": return ctx.mkLe(lhs.toZ3(map, ctx), rhs.toZ3(map, ctx));
    //        case ">": return ctx.mkGt(lhs.toZ3(map, ctx), rhs.toZ3(map, ctx));
    //        case ">=": return ctx.mkGe(lhs.toZ3(map, ctx), rhs.toZ3(map, ctx));
    //    }
    //    System.out.println(String.format("Check toz3() for %s", this));
    //    return null;
    //}

    // from AExpr:
    //public ArithExpr toZ3(MapSSA map, Context ctx) {
    //    switch(op) {
    //        case "+": return ctx.mkAdd(lhs.toZ3(map, ctx), rhs.toZ3(map, ctx));
    //        case "-": return ctx.mkSub(lhs.toZ3(map, ctx), rhs.toZ3(map, ctx));
    //        case "*": return ctx.mkMul(lhs.toZ3(map, ctx), rhs.toZ3(map, ctx));
    //        case "/": return ctx.mkDiv(lhs.toZ3(map, ctx), rhs.toZ3(map, ctx));
    //        case "xor": return ctx.mkBV2Int(ctx.mkBVXOR(ctx.mkInt2BV(32, (IntExpr) lhs.toZ3(map, ctx)), ctx.mkInt2BV(32, (IntExpr) rhs.toZ3(map, ctx))), false);
    //    }
    //    System.out.println(String.format("Check toZ3() for %s", this));
    //    return null;
    //}


    //public Set<Register> getRegs() {
    //    Set<Register> setRegs = new HashSet<Register>();
    //    setRegs.addAll(leftExpression.getRegs());
    //    setRegs.addAll(rightExpression.getRegs());
    //    return setRegs;
    //}
}