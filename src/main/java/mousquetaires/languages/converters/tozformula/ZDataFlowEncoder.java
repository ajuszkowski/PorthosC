package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.XUnrolledProgram;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperator;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryOperator;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XLvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitorBase;
import mousquetaires.languages.syntax.xgraph.visitors.XVisitorIllegalStateException;
import mousquetaires.languages.syntax.zformula.*;
import mousquetaires.utils.exceptions.NotImplementedException;


class ZDataFlowEncoder extends XEventVisitorBase<ZLogicalFormula> {

    private final ZStaticSingleAssignmentMap ssaMap;
    //private final ZOperatorEncoder operatorEncoder;

    ZDataFlowEncoder(XUnrolledProgram program) {
        this.ssaMap = new ZStaticSingleAssignmentMap(program.size());
        //this.operatorEncoder = new ZOperatorEncoder();
    }

    public void updateReferences(XEvent current, XEvent parent) {
        ssaMap.copyValues(parent, current);
    }

    public ZLogicalFormula encodeOrNull(XEvent current) {
        try {
            return current.accept(this);//TODO
        }
        catch (XVisitorIllegalStateException e) {
            return null;
        }
    }

    @Override
    public ZLogicalFormula visit(XUnaryComputationEvent event) {
        ZVariableReferenceMap map = ssaMap.getEventMapOrThrow(event);
        ZUnaryOperator operator = XToZOperatorConverter.convert(event.getOperator());
        ZAtom operand = map.getReferenceOrThrow(event.getOperand());
        return operator.create(operand);
    }

    @Override
    public ZLogicalFormula visit(XBinaryComputationEvent event) {
        ZVariableReferenceMap map = ssaMap.getEventMapOrThrow(event);
        ZBinaryOperator operator = XToZOperatorConverter.convert(event.getOperator());
        ZAtom left = map.getReferenceOrThrow(event.getFirstOperand());
        ZAtom right = map.getReferenceOrThrow(event.getSecondOperand());
        return operator.create(left, right);
    }



    @Override
    public ZLogicalFormula visit(XStoreMemoryEvent event) {
        ZVariableReferenceMap map = ssaMap.getEventMapOrThrow(event);
        ZAtom srcLocal = map.getReferenceOrThrow(event.getSource());
        ZAtom dstShared = map.updateReference(event.getDestination());
        return ZBinaryOperator.CompareEquals.create(srcLocal, dstShared);
    }

    @Override
    public ZLogicalFormula visit(XLoadMemoryEvent event) {
        ZVariableReferenceMap map = ssaMap.getEventMapOrThrow(event);
        ZAtom srcShared = map.updateReference(event.getSource());
        XLocalMemoryUnit dst = event.getDestination();
        if (!(dst instanceof XLvalueMemoryUnit)) {
            throw new IllegalArgumentException("Load event destination must be lvalue");
        }
        XLvalueMemoryUnit dstLvalue = (XLvalueMemoryUnit) dst;
        ZAtom dstLocal = map.updateReference(dstLvalue);
        return ZBinaryOperator.CompareEquals.create(srcShared, dstLocal);
    }
}
