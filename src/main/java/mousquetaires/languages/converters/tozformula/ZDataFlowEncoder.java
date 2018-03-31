package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.XUnrolledProgram;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitorBase;
import mousquetaires.languages.syntax.xgraph.visitors.XVisitorIllegalStateException;
import mousquetaires.languages.syntax.zformula.XZOperator;
import mousquetaires.languages.syntax.zformula.ZFormula;
import mousquetaires.languages.syntax.zformula.ZBoolFormula;
import mousquetaires.utils.exceptions.NotImplementedException;


class ZDataFlowEncoder extends XEventVisitorBase<ZBoolFormula> {

    private final ZStaticSingleAssignmentMap ssaMap;
    //private final ZOperatorEncoder operatorEncoder;

    ZDataFlowEncoder(XUnrolledProgram program) {
        this.ssaMap = new ZStaticSingleAssignmentMap(program.size());
        //this.operatorEncoder = new ZOperatorEncoder();
    }

    public void updateReferences(XEvent current, XEvent parent) {
        ssaMap.copyValues(parent, current);
    }

    public ZBoolFormula encodeOrNull(XEvent current) {
        try {
            return current.accept(this);//TODO
        }
        catch (XVisitorIllegalStateException e) {
            return null;
        }
    }

    @Override
    public ZBoolFormula visit(XNullaryComputationEvent event) {
        return null; // no dataflow transition here
    }

    @Override
    public ZBoolFormula visit(XUnaryComputationEvent event) {
        throw new NotImplementedException(); // ?
    }

    @Override
    public ZBoolFormula visit(XBinaryComputationEvent event) {
        ZVariableReferenceMap map = ssaMap.getEventMapOrThrow(event);
        ZFormula left = map.getReferenceOrThrow(event.getFirstOperand());
        ZFormula right = map.getReferenceOrThrow(event.getSecondOperand());
        return XZOperator.CompareEquals.create(left, right);
    }

    @Override
    public ZBoolFormula visit(XStoreMemoryEvent event) {
        ZVariableReferenceMap map = ssaMap.getEventMapOrThrow(event);
        ZFormula srcLocal = map.getReferenceOrThrow(event.getSource());
        ZFormula dstShared = map.updateReference(event.getDestination());
        return XZOperator.CompareEquals.create(srcLocal, dstShared);
    }

    @Override
    public ZBoolFormula visit(XLoadMemoryEvent event) {
        ZVariableReferenceMap map = ssaMap.getEventMapOrThrow(event);
        ZFormula srcShared = map.updateReference(event.getSource());
        ZFormula dstLocal = map.updateReference(event.getDestination());
        return XZOperator.CompareEquals.create(srcShared, dstLocal);
    }
}
