package mousquetaires.languages.ytree.expressions;

import java.util.Objects;


public class YBinaryExpression extends YExpression {
    public enum OperatorKind {
        Plus,
        Minus,
        Multiply,
        Equals,
    }

    private YExpression leftExpression;
    private YExpression rightExpression;
    private OperatorKind operator;

    public YBinaryExpression(OperatorKind operator,
                             YExpression leftExpression,
                             YExpression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }


    //public String toString() {
    //    return String.format("(%s %s %s)", leftExpression, operator, rightExpression);
    //}
    //
    //public YBinaryExpression clone() {
    //    YBinaryExpression newB1 = leftExpression.clone();
    //    YBinaryExpression newB2 = rightExpression.clone();
    //    return new YBinaryExpression(newB1, operator, newB2);
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


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(leftExpression).append(" ");
        switch (operator) {
            case Plus:
                sb.append("+");
                break;
            case Minus:
                sb.append("-");
                break;
            case Multiply:
                sb.append("*");
                break;
            case Equals:
                sb.append("==");
                break;
        }
        sb.append(" ").append(rightExpression);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YBinaryExpression)) return false;
        YBinaryExpression that = (YBinaryExpression) o;
        return Objects.equals(leftExpression, that.leftExpression) &&
                Objects.equals(rightExpression, that.rightExpression) &&
                operator == that.operator;
    }

    @Override
    public int hashCode() {

        return Objects.hash(leftExpression, rightExpression, operator);
    }
}
