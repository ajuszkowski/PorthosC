package mousquetaires.languages.syntax.ytree.expressions.binary;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.visitors.ytree.YtreeVisitor;


public class YRelativeBinaryExpression extends YBinaryExpression {
    public enum Kind implements YBinaryExpression.Kind {
        Equals,
        NotEquals,
        Greater,
        GreaterOrEquals,
        Less,
        LessOrEquals,
        ;

        @Override
        public String toString() {
            switch (this) {
                case Equals:          return "==";
                case NotEquals:       return "!=";
                case Greater:         return ">";
                case GreaterOrEquals: return ">=";
                case Less:            return "<";
                case LessOrEquals:    return "<=";
                default:
                    throw new IllegalArgumentException(this.name());
            }
        }

        @Override
        public YRelativeBinaryExpression createExpression(YExpression leftExpression, YExpression rightExpression) {
            return new YRelativeBinaryExpression(this, leftExpression, rightExpression);
        }
    }

    protected YRelativeBinaryExpression(Kind operator, YExpression leftExpression, YExpression rightExpression) {
        super(operator, leftExpression, rightExpression);
    }

    @Override
    public YRelativeBinaryExpression.Kind getKind() {
        return (YRelativeBinaryExpression.Kind) super.getKind();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YRelativeBinaryExpression copy() {
        return new YRelativeBinaryExpression(getKind(), getLeftExpression(), getRightExpression());
    }

    @Override
    public String toString() {
        return getLeftExpression() + " " + getKind() + " " + getRightExpression();
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


}
