package mousquetaires.languages.converters.tosmt.toz3;

import com.microsoft.z3.*;
import com.microsoft.z3.Expr;
import mousquetaires.languages.syntax.zformula.*;
import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;
import org.apache.xpath.operations.Bool;

import java.util.ArrayList;
import java.util.List;


public class ZFormulaToZ3Converter implements ZformulaVisitor<Expr> {

    private final Context ctx;

    public ZFormulaToZ3Converter() {
        this(new Context());
    }

    public ZFormulaToZ3Converter(Context ctx) {
        this.ctx = ctx;
    }

    public Expr convert(ZFormula formula) {
        return formula.accept(this);
    }


    @Override
    public Expr visit(ZGlobalVariable variable) {
        return ctx.mkBoolConst(variable.getName());
    }

    @Override
    public Expr visit(ZIndexedVariable variable) {
        Z3Type z3type = Z3Type.convert(variable.getBitness());
        String name = variable.getName() + "_" + variable.getIndex();
        return makeVariable(z3type, name);
    }

    @Override
    public Expr visit(ZConstant constant) {
        Z3Type z3type = Z3Type.convert(constant.getBitness());
        return makeConstant(z3type, constant.getValue());
    }

    @Override
    public BoolExpr visit(ZLogicalNegation formula) {
        BoolExpr expression = visitBool(formula.getExpression());
        return ctx.mkNot(expression);
    }

    @Override
    public Expr visit(ZUnaryOperation formula) {
        // here we need to consider more Z3 types: bitvector, bitwise operations, ... see https://rise4fun.com/z3/tutorialcontent/guide
        throw new NotImplementedException();
        //ArithExpr operand = visitArith(formula.getOperand());
        //ZUnaryOperator operator = formula.getOperator();
        //switch (operator) {
        //    case BitNegation:
        //        return ctx.mkBVNot(operand)
        //    case NoOperation:
        //        break;
        //}
    }

    @Override
    public Expr visit(ZBinaryOperation formula) {
        ZAtom left = formula.getLeft();
        ZAtom right = formula.getRight();
        ZBinaryOperator operator = formula.getOperator();
        switch (operator) {
            case Addition:
                return ctx.mkAdd(visitArith(left), visitArith(right));
            case Subtraction:
                return ctx.mkSub(visitArith(left), visitArith(right));
            case Multiplication:
                return ctx.mkMul(visitArith(left), visitArith(right));
            case Division:
                return ctx.mkDiv(visitArith(left), visitArith(right));
            case Modulo:
                return ctx.mkMod(visitInt(left), visitInt(right));
            case LeftShift:
                // todo: need to consider signed/unsigned type here, see https://rise4fun.com/z3/tutorialcontent/guide
                //return ctx.mkBVSHL(visitBitVec(left), visitBitVec(right));
                throw new NotImplementedException();
            case RightShift:
                throw new NotImplementedException();
            case BitAnd:
                throw new NotImplementedException();
            case BitOr:
                throw new NotImplementedException();
            case BitXor:
                throw new NotImplementedException();
            case CompareEquals:
                return ctx.mkEq(visitExpr(left), visitExpr(right));
            case CompareNotEquals:
                return ctx.mkNot(ctx.mkEq(visitExpr(left), visitExpr(right)));
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
    public BoolExpr visit(ZLogicalImplication formula) {
        BoolExpr left = visitBool(formula.getLeftExpression());
        BoolExpr right = visitBool(formula.getRightExpression());
        return ctx.mkImplies(left, right);
    }

    @Override
    public BoolExpr visit(ZLogicalDisjunction formula) {
        BoolExpr[] disjuncts = convertExpressions(formula.getExpressions());
        return ctx.mkOr(disjuncts);
    }

    @Override
    public BoolExpr visit(ZLogicalConjunction formula) {
        BoolExpr[] conjuncts = convertExpressions(formula.getExpressions());
        return ctx.mkAnd(conjuncts);
    }


    private BoolExpr visitBool(ZLogicalFormula formula) {
        Expr result = formula.accept(this);
        if (!(result instanceof BoolExpr)) {
            throw new IllegalStateException(); //todo: message + exception type
        }
        return (BoolExpr) result;
    }

    private ArithExpr visitArith(ZAtom formula) {
        // todo: type checking here
        return (ArithExpr) formula.accept(this);
    }

    private IntExpr visitInt(ZAtom formula) {
        // todo: type checking here
        return (IntExpr) formula.accept(this);
    }

    //private BitVecExpr visitBitVec(ZAtom formula) {
    //    // todo: type checking here
    //    return (BitVecExpr) formula.accept(this);
    //}

    private Expr visitExpr(ZFormula formula) {
        return formula.accept(this);
    }

    private BoolExpr[] convertExpressions(List<ZLogicalFormula> expressions) {
        List<BoolExpr> disjunctList = new ArrayList<>(expressions.size());
        for (ZLogicalFormula expression : expressions) {
            disjunctList.add(visitBool(expression));
        }
        return disjunctList.toArray(new BoolExpr[0]);
    }

    private Expr makeVariable(Z3Type z3Type, String name) {
        switch (z3Type) {
            case Bool: {
                return ctx.mkBoolConst(name);
            }
            case Int: {
                return ctx.mkIntConst(name);
            }
            case Real: {
                throw new NotImplementedException();
            }
            case BitVector: {
                throw new NotImplementedException();
            }
            default: {
                throw new IllegalArgumentException(z3Type.name());
            }
        }
    }

    private Expr makeConstant(Z3Type z3Type, Object argument) {
        switch (z3Type) {
            case Bool: {
                if (!(argument instanceof Boolean)) {
                    throw new ToSmtConversionException("unexpected constant type: " + argument.getClass().getSimpleName());
                }
                return ctx.mkBool((Boolean) argument);
            }
            case Int:
                if (!(argument instanceof Integer)) {
                    throw new ToSmtConversionException("unexpected constant type: " + argument.getClass().getSimpleName());
                }
                return ctx.mkInt((Integer) argument);
            case Real: {
                throw new NotImplementedException();
            }
            case BitVector: {
                throw new NotImplementedException();
            }
            default:
                throw new IllegalArgumentException(z3Type.name());
        }
    }

}
