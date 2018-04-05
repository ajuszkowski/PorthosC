package mousquetaires.languages.converters.tozformula;

import com.microsoft.z3.*;
import dartagnan.utils.Utils;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static mousquetaires.utils.StringUtils.wrap;


class VarRefCollection implements Cloneable {

    private final Map<XLvalueMemoryUnit, Integer> map;
    private final XMemoryUnitEncoder encoder;

    VarRefCollection(XProcessId processId, Context ctx) {
        this(new HashMap<>(), processId, ctx);
    }

    private VarRefCollection(Map<XLvalueMemoryUnit, Integer> map, XProcessId processId, Context ctx) {
        this.map = map;
        this.encoder = new XMemoryUnitEncoder(processId, ctx);
    }

    public void addAll(VarRefCollection other) {
        for (Map.Entry<XLvalueMemoryUnit, Integer> pair : other.map.entrySet()) {
            XLvalueMemoryUnit memoryUnit = pair.getKey();
            int otherIndex = pair.getValue();
            int index = map.containsKey(memoryUnit)
                    ? Integer.max(otherIndex, map.get(memoryUnit))
                    : otherIndex;
            map.put(memoryUnit, index);
        }
    }

    public void addNew(XLvalueMemoryUnit memoryUnit) {
        add(memoryUnit, 0);
    }

    public void add(XLvalueMemoryUnit memoryUnit, int index) {
        assert !map.containsKey(memoryUnit) : "already contains key: " + memoryUnit;
        map.put(memoryUnit, index);
    }

    public Set<XLvalueMemoryUnit> getVars() {
        return map.keySet();
    }

    public boolean containsVarRef(XLvalueMemoryUnit memoryUnit) {
        return map.containsKey(memoryUnit);
    }

    public Expr updateRef(XLvalueMemoryUnit memoryUnit) {
        int index = getRefIndex(memoryUnit);
        map.put(memoryUnit, index + 1);
        return encodeVar(memoryUnit);
    }

    public void resetRef(XLvalueMemoryUnit memoryUnit, int newIndex) {
        assert map.containsKey(memoryUnit) : "attempt to reset non-set value";
        map.put(memoryUnit, newIndex);
    }

    public Expr encodeVar(XMemoryUnit memoryUnit) {
        return memoryUnit.accept(encoder);
    }

    public int getRefIndex(XLvalueMemoryUnit memoryUnit) {
        if (!map.containsKey(memoryUnit)) {
            throw new IllegalStateException("key " + wrap(memoryUnit) + " not found"); // TODO: more eloquent message
        }
        return map.get(memoryUnit);
    }

    public static VarRefCollection copy(VarRefCollection collection) {
        return new VarRefCollection(new HashMap<>(collection.map), collection.encoder.processId, collection.encoder.ctx);
    }

    @Override
    public String toString() {
        return "[" + map + "]";
    }


    class XMemoryUnitEncoder implements XMemoryUnitVisitor<Expr> {
        private final XProcessId processId;
        private final Context ctx;

        XMemoryUnitEncoder(XProcessId processId, Context ctx) {
            this.processId = processId;
            this.ctx = ctx;
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
            return Utils.ssaReg(register, getRefIndex(register), ctx);
        }

        @Override
        public Expr visit(XLocation location) {
            return Utils.ssaLoc(location, processId, getRefIndex(location), ctx);
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
