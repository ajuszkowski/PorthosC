package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.XUnrolledProgram;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XNullaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitorBase;
import mousquetaires.languages.syntax.zformula.ZBoolFormula;
import mousquetaires.utils.CollectionUtils;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.exceptions.encoding.XVisitorIllegalStateException;

import java.util.Set;


class ZDataFlowEncoder extends XEventVisitorBase<ZBoolFormula> {

    private final ZStaticSingleAssignmentMap ssaMap;
    //private final ZOperatorEncoder operatorEncoder;

    public ZDataFlowEncoder(XUnrolledProgram program) {
        this.ssaMap = new ZStaticSingleAssignmentMap();
        //this.operatorEncoder = new ZOperatorEncoder();
    }

    public void updateReferences(XEvent current, Set<XEvent> parents) {
        if (parents.size() == 1) {
            XEvent parent = CollectionUtils.getSingleElement(parents);
            ssaMap.copyValues(current, parent);
        }
        else {
            assert parents.size() > 1 : parents.size();
            throw new NotImplementedException();
        }
    }

    public ZBoolFormula encodeOrNull(XEvent current) {
        try {
            return current.accept(this);
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
        throw new NotImplementedException();
    }

    @Override
    public ZBoolFormula visit(XBinaryComputationEvent event) {
        XLocalMemoryUnit left = event.getFirstOperand();
        XLocalMemoryUnit right = event.getSecondOperand();

        throw new NotImplementedException();
    }

    @Override
    public ZBoolFormula visit(XStoreMemoryEvent event) {
        throw new NotImplementedException();
    }

    @Override
    public ZBoolFormula visit(XLoadMemoryEvent event) {
        throw new NotImplementedException();
    }
}
