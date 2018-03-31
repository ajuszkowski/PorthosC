package mousquetaires.languages.converters.tosmt.toz3;

import com.microsoft.z3.*;
import com.microsoft.z3.Expr;
import mousquetaires.languages.syntax.zformula.XZOperator;
import mousquetaires.languages.syntax.zformula.*;
import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

import static mousquetaires.languages.syntax.zformula.ZBoolFormulaFactory.not;


public class ZFormulaToZ3Converter implements ZformulaVisitor<Expr> {

    private final Context ctx;

    public ZFormulaToZ3Converter() {
        this(new Context());
    }

    public ZFormulaToZ3Converter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public BoolExpr visit(ZVariable variable) {
        return ctx.mkBoolConst(variable.getName());
    }

    @Override
    public BoolExpr visit(ZVariableReference variable) {
        return ctx.mkBoolConst(variable.getName() + "_" + variable.getIndex());
    }

    @Override
    public BoolExpr visit(ZConstant constant) {
        return ctx.mkBoolConst(constant.getName());
    }

    @Override
    public BoolExpr visit(ZBoolNegation formula) {
        BoolExpr expression = visitBool(formula.getExpression());
        return ctx.mkNot(expression);
    }

    @Override
    public BoolExpr visit(ZBoolConstant formula) {
        return ctx.mkBoolConst(formula.name());
    }

    @Override
    public Expr visit(ZBoolOperation formula) {
        ZFormula left = formula.getLeft();
        ZFormula right = formula.getRight();
        XZOperator operator = formula.getOperator();
        switch (operator) {
            case LogicalAnd:
                throw new NotImplementedException();
            case LogicalOr:
                throw new NotImplementedException();
            case LogicalNot:
                throw new NotImplementedException();
            case IntegerPlus:
                return ctx.mkAdd(visitArith(left), visitArith(right));
            case IntegerMinus:
                return ctx.mkSub(visitArith(left), visitArith(right));
            case IntegerMultiply:
                return ctx.mkMul(visitArith(left), visitArith(right));
            case IntegerDivide:
                return ctx.mkDiv(visitArith(left), visitArith(right));
            case IntegerModulo:
                throw new NotImplementedException();
            case IntegerLeftShift:
                throw new NotImplementedException();
            case IntegerRightShift:
                throw new NotImplementedException();
            case BitAnd:
                throw new NotImplementedException();
            case BitOr:
                throw new NotImplementedException();
            case BitXor:
                throw new NotImplementedException();
            case BitNot:
                throw new NotImplementedException();
            case CompareEquals:
                return ctx.mkEq(visitFormula(left), visitFormula(right));
            case CompareNotEquals:
                ZBoolFormula rewrapped = not(XZOperator.CompareEquals.create(left, right));
                return visitBool(rewrapped);
            case CompareLess:
                return ctx.mkLt(visitArith(left), visitArith(right));
            case CompareLessOrEquals:
                return ctx.mkLe(visitArith(left), visitArith(right));
            case CompareGreater:
                return ctx.mkGt(visitArith(left), visitArith(right));
            case CompareGreaterOrEquals:
                return ctx.mkGe(visitArith(left), visitArith(right));
            default:
                throw new IllegalArgumentException(operator.name());
        }
    }

    @Override
    public BoolExpr visit(ZBoolImplication formula) {
        BoolExpr left = visitBool(formula.getLeftExpression());
        BoolExpr right = visitBool(formula.getRightExpression());
        return ctx.mkImplies(left, right);
    }

    @Override
    public BoolExpr visit(ZBoolDisjunction formula) {
        BoolExpr[] disjuncts = convertExpressions(formula.getExpressions());
        return ctx.mkAnd(disjuncts);
    }

    @Override
    public BoolExpr visit(ZBoolConjunction formula) {
        BoolExpr[] conjuncts = convertExpressions(formula.getExpressions());
        return ctx.mkOr(conjuncts);
    }

    private BoolExpr visitBool(ZBoolFormula formula) {
        Expr result = formula.accept(this);
        if (!(result instanceof BoolExpr)) {
            throw new IllegalStateException();
        }
        return (BoolExpr) result;
    }

    private ArithExpr visitArith(ZFormula formula) {
        // todo: type checking here
        return (ArithExpr) formula.accept(this);
    }

    private Expr visitFormula(ZFormula formula) {
        return formula.accept(this);
    }

    private BoolExpr[] convertExpressions(List<ZBoolFormula> expressions) {
        List<BoolExpr> disjunctList = new ArrayList<>(expressions.size());
        for (ZBoolFormula expression : expressions) {
            disjunctList.add(visitBool(expression));
        }
        return disjunctList.toArray(new BoolExpr[0]);
    }
}
