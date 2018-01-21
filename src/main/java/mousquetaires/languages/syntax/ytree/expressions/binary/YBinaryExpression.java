package mousquetaires.languages.syntax.ytree.expressions.binary;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


public abstract class YBinaryExpression extends YMultiExpression {

    protected interface Kind {
        YBinaryExpression createExpression(YExpression leftExpression, YExpression rightExpression);
    }

    private final YBinaryExpression.Kind kind;

    YBinaryExpression(YBinaryExpression.Kind kind,
                             YExpression leftExpression,
                             YExpression rightExpression) {
        super(leftExpression, rightExpression);
        this.kind = kind;
    }

    public YBinaryExpression.Kind getKind() {
        return kind;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(getLeftExpression(), getRightExpression());
    }

    public YExpression getLeftExpression() {
        return getElements()[0];
    }

    public YExpression getRightExpression() {
        return getElements()[1];
    }

    //public String toString() {
    //    return String.format("(%s %s %s)", leftExpression, kind, rightExpression);
    //}
    //
    //public YBinaryExpression clone() {
    //    YBinaryExpression newB1 = leftExpression.clone();
    //    YBinaryExpression newB2 = rightExpression.clone();
    //    return new YBinaryExpression(newB1, kind, newB2);
    //}

    //public BoolExpr toZ3(MapSSA map, Context ctx) {
    //    switch(kind) {
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
