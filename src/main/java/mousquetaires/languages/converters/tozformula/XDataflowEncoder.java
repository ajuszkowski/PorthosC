package mousquetaires.languages.converters.tozformula;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XNopEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XInitialWriteEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XLvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;


class XDataflowEncoder implements XEventVisitor<BoolExpr> {

    private final Context ctx;
    private final StaticSingleAssignmentMap ssaMap;

    public XDataflowEncoder(Context ctx, StaticSingleAssignmentMap ssaMap) {
        this.ctx = ctx;
        this.ssaMap = ssaMap;
    }

    public BoolExpr encodeGuard(XComputationEvent guard) {
        // ???
        // should encode
        Expr encoded = ssaMap.getEventMap(guard).encodeVar(guard);
        if (encoded != null) {
            if (encoded instanceof BoolExpr) {
                return (BoolExpr) encoded;
            }
            throw new ToZ3ConversionException("Detected non-boolean guard: " + guard);
        }
        throw new ToZ3ConversionException("Could not encode guard: " + guard);
    }

    @Override
    public BoolExpr visit(XInitialWriteEvent event) {
        throw new NotImplementedException();
    }

    @Override
    public BoolExpr visit(XStoreMemoryEvent event) {
        Expr srcLocal = encodeVar(event.getSource(), event);
        Expr dstShared = updateVarRef(event.getDestination(), event);
        return ctx.mkEq(srcLocal, dstShared);
    }

    @Override
    public BoolExpr visit(XLoadMemoryEvent event) {
        Expr srcShared = updateVarRef(event.getSource(), event);
        Expr dstLocal = updateVarRef(event.getDestination(), event);
        return ctx.mkEq(srcShared, dstLocal);
    }

    @Override
    public BoolExpr visit(XRegisterMemoryEvent event) {
        Expr srcLocal = encodeVar(event.getSource(), event);
        Expr dstLocal = updateVarRef(event.getDestination(), event);
        return ctx.mkEq(srcLocal, dstLocal);
    }

    @Override
    public BoolExpr visit(XUnaryComputationEvent event) {
        // no data-flow // ????? TODO: where do we encode the computation 'x + y', 'true && false' ???
        return null;
    }

    @Override
    public BoolExpr visit(XBinaryComputationEvent event) {
        // no data-flow // ????? TODO: where do we encode the computation 'x + y', 'true && false' ???
        return null;
    }

    @Override
    public BoolExpr visit(XEntryEvent event) {
        return null;
    }

    @Override
    public BoolExpr visit(XExitEvent event) {
        return null;
    }

    @Override
    public BoolExpr visit(XJumpEvent event) {
        return null;
    }

    @Override
    public BoolExpr visit(XNopEvent event) {
        return null;
    }

    private Expr encodeVar(XLocalMemoryUnit unit, XEvent event) {
        VarRefCollection refsCollection = ssaMap.getEventMap(event);
        return refsCollection.encodeVar(unit);
    }

    private Expr updateVarRef(XLvalueMemoryUnit unit, XEvent event) {
        VarRefCollection refsCollection = ssaMap.getEventMap(event);
        return refsCollection.updateRef(unit);
    }
}
