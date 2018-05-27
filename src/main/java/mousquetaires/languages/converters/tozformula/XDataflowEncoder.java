package mousquetaires.languages.converters.tozformula;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XAssertionEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XFunctionCallEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XNopEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.*;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;


public class XDataflowEncoder implements XEventVisitor<BoolExpr> {

    private final Context ctx;
    private final XMemoryUnitEncoder memoryUnitEncoder;

    public XDataflowEncoder(Context ctx, StaticSingleAssignmentMap ssaMap) {
        this.ctx = ctx;
        this.memoryUnitEncoder = new XMemoryUnitEncoder(ctx, ssaMap);
    }

    public Expr encodeMemoryUnit(XMemoryUnit memoryUnit, XEvent event) {
        return memoryUnitEncoder.encodeVar(memoryUnit, event);
    }

    public BoolExpr encodeGuard(XComputationEvent guard) {
        XComputationEvent guardMemoryUnit = guard;
        Expr encoded = memoryUnitEncoder.encodeVar(guardMemoryUnit, guard);
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
        Expr srcLocal  = memoryUnitEncoder.encodeVar(event.getSource(), event);
        Expr dstShared = memoryUnitEncoder.updateVarRef(event.getDestination(), event);
        return ctx.mkEq(srcLocal, dstShared);
    }

    @Override
    public BoolExpr visit(XLoadMemoryEvent event) {
        Expr srcShared = memoryUnitEncoder.updateVarRef(event.getSource(), event);
        Expr dstLocal  = memoryUnitEncoder.updateVarRef(event.getDestination(), event);
        return ctx.mkEq(srcShared, dstLocal);
    }

    @Override
    public BoolExpr visit(XRegisterMemoryEvent event) {
        Expr srcLocal = memoryUnitEncoder.encodeVar(event.getSource(), event);
        Expr dstLocal = memoryUnitEncoder.updateVarRef(event.getDestination(), event);
        return ctx.mkEq(srcLocal, dstLocal);
    }

    @Override
    public BoolExpr visit(XUnaryComputationEvent event) {
        // no data-flow
        return null;
    }

    @Override
    public BoolExpr visit(XBinaryComputationEvent event) {
        // no data-flow
        return null;
    }

    @Override
    public BoolExpr visit(XAssertionEvent event) {
        return memoryUnitEncoder.encodeAssertion(event);
    }

    @Override
    public BoolExpr visit(XFunctionCallEvent event) {
        throw new NotImplementedException();
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

    @Override
    public BoolExpr visit(XBarrierEvent event) {
        return null;
    }

}