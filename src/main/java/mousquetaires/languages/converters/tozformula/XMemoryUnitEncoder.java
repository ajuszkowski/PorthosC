package mousquetaires.languages.converters.tozformula;

import com.microsoft.z3.*;
import dartagnan.utils.Utils;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.Map;


class XMemoryUnitEncoder {

    private final Context ctx;
    private final StaticSingleAssignmentMap ssaMap;

    XMemoryUnitEncoder(Context ctx, StaticSingleAssignmentMap ssaMap) {
        this.ctx = ctx;
        this.ssaMap = ssaMap;
    }

    public Expr encodeVar(XLocalMemoryUnit unit, XEvent accessingEvent) {
        XMemoryUnitEncoderVisitor visitor = getVisitor(accessingEvent);
        return unit.accept(visitor);
    }

    public Expr updateVarRef(XLvalueMemoryUnit unit, XEvent event) {
        VarRefCollection refsCollection = ssaMap.getEventMap(event);
        refsCollection.updateRef(unit);
        XMemoryUnitEncoderVisitor visitor = createVisitor(event.getProcessId(), refsCollection);
        return unit.accept(visitor);
    }


    private XMemoryUnitEncoderVisitor getVisitor(XEvent event) {
        return createVisitor(event.getProcessId(), ssaMap.getEventMap(event));
    }

    private XMemoryUnitEncoderVisitor createVisitor(XProcessId processId, VarRefCollection varRefCollection) {
        return new XMemoryUnitEncoderVisitor(processId, varRefCollection);
    }

    private final Expr encodeRegister(XRegister register, int index) {
        return Utils.ssaReg(register, index, ctx);
    }

    private final Expr encodeLocation(XLocation location, XProcessId processId, int index) {
        return Utils.ssaLoc(location, processId, index, ctx);
    }


    private final class XMemoryUnitEncoderVisitor implements XMemoryUnitVisitor<Expr> {

        private final XProcessId processId;
        private final VarRefCollection varRefCollection;

        public XMemoryUnitEncoderVisitor(XProcessId processId, VarRefCollection varRefCollection) {
            this.processId = processId;
            this.varRefCollection = varRefCollection;
        }

        @Override
        public Expr visit(XUnaryComputationEvent event) {
            Expr operand = event.getOperand().accept(this);
            // for now, unary operators are only boolean
            if (!(operand instanceof BoolExpr)) {
                throw new ToZ3ConversionException("Expected boolean operand of unary computation event "
                                                          + event + ", found: " + operand.getClass().getSimpleName());
            }
            BoolExpr boolOperand = (BoolExpr) operand;
            switch (event.getOperator()) {
                case BitNegation:
                    return ctx.mkNot(boolOperand);
                case NoOperation:
                    return boolOperand;
                default:
                    throw new IllegalArgumentException(event.getOperator().name());
            }
        }

        @Override
        public Expr visit(XBinaryComputationEvent event) {
            // no need to visit children recursively here, X is already linearised structure
            Expr op1 = event.getFirstOperand().accept(this);
            Expr op2 = event.getSecondOperand().accept(this);
            switch (event.getOperator()) {
                case Addition:
                    return ctx.mkAdd(asArithExpr(op1), asArithExpr(op2));
                case Subtraction:
                    return ctx.mkSub(asArithExpr(op1), asArithExpr(op2));
                case Multiplication:
                    return ctx.mkMul(asArithExpr(op1), asArithExpr(op2));
                case Division:
                    return ctx.mkDiv(asArithExpr(op1), asArithExpr(op2));
                case Modulo:
                    throw new NotImplementedException(); // mkMod ?

                case LeftShift:
                    throw new NotImplementedException(); // bit vectors yet not supported
                case RightShift:
                    throw new NotImplementedException(); // bit vectors yet not supported
                case BitAnd:
                    throw new NotImplementedException(); // bit vectors yet not supported
                case BitOr:
                    throw new NotImplementedException(); // bit vectors yet not supported
                case BitXor:
                    throw new NotImplementedException(); // bit vectors yet not supported

                case CompareEquals:
                    return ctx.mkEq(op1, op2);
                case CompareNotEquals:
                    return ctx.mkNot(ctx.mkEq(op1, op2));

                case CompareLess:
                    return ctx.mkLt(asArithExpr(op1), asArithExpr(op2));
                case CompareLessOrEquals:
                    return ctx.mkLe(asArithExpr(op1), asArithExpr(op2));
                case CompareGreater:
                    return ctx.mkGt(asArithExpr(op1), asArithExpr(op2));
                case CompareGreaterOrEquals:
                    return ctx.mkGe(asArithExpr(op1), asArithExpr(op2));

                case Conjunction:
                    return ctx.mkAnd(asBoolExpr(op1), asBoolExpr(op2));
                case Disjunction:
                    return ctx.mkOr(asBoolExpr(op1), asBoolExpr(op2));
                default:
                    throw new IllegalArgumentException(event.getOperator().name());
            }
        }

        @Override
        public Expr visit(XConstant constant) {
            // TODO: determine type of constant here
            return ctx.mkInt(constant.getValue());
        }

        @Override
        public Expr visit(XRegister register) {
            return encodeRegister(register, varRefCollection.getRefIndex(register));
        }

        @Override
        public Expr visit(XLocation location) {
            return encodeLocation(location, processId, varRefCollection.getRefIndex(location));
        }

        private ArithExpr asArithExpr(Expr expr) {
            if (!(expr instanceof ArithExpr)) {
                throw new ToZ3ConversionException("expression " + expr + " is supposed to be of type ArithExpr");
            }
            return (ArithExpr) expr;
        }

        private IntExpr asIntExpr(Expr expr) {
            if (!(expr instanceof IntExpr)) {
                throw new ToZ3ConversionException("expression " + expr + " is supposed to be of type IntExpr");
            }
            return (IntExpr) expr;
        }

        private BoolExpr asBoolExpr(Expr expr) {
            if (!(expr instanceof BoolExpr)) {
                throw new ToZ3ConversionException("expression " + expr + " is supposed to be of type BoolExpr");
            }
            return (BoolExpr) expr;
        }
    }
}
