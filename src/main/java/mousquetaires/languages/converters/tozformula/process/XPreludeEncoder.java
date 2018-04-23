package mousquetaires.languages.converters.tozformula.process;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.converters.tozformula.StaticSingleAssignmentMap;
import mousquetaires.languages.converters.tozformula.XDataflowEncoder;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.process.XProcess;

import java.util.*;


public class XPreludeEncoder implements XProcessEncoder {

    private final Context ctx;
    private final StaticSingleAssignmentMap ssaMap;
    private final XDataflowEncoder dataFlowEncoder;

    public XPreludeEncoder(Context ctx, StaticSingleAssignmentMap ssaMap, XDataflowEncoder dataFlowEncoder) {
        this.ctx = ctx;
        this.ssaMap = ssaMap;
        this.dataFlowEncoder = dataFlowEncoder;
    }

    @Override
    public List<BoolExpr> encodeProcess(XProcess process) {
        List<BoolExpr> asserts = new ArrayList<>();
        Iterator<XEvent> nodesIterator = process.linearisedNodesIterator();
        // execute the entry event indefinitely:
        asserts.add(process.source().executes(ctx));

        while (nodesIterator.hasNext()) {
            XEvent currentEvent = nodesIterator.next();
            BoolExpr currentVar = currentEvent.executes(ctx);

            // TODO: this is bad, that dataFlowEncoder implicitly depends on ssaMap: ssa-map update must be inside data-flow encoder!
            for (boolean edgeKind : FlowGraph.edgeKinds()) {
                if (!process.hasParent(edgeKind, currentEvent)) {
                    continue;
                }
                Set<XEvent> parents = process.parents(edgeKind, currentEvent);
                assert parents.size() > 0 : "disconnected graph";
                for (XEvent parent : parents) {
                    ssaMap.updateRefs(currentEvent, parent);
                }
            }

            // encode event's inner data-flow:
            BoolExpr currentEventDataFlowAssert = currentEvent.accept(dataFlowEncoder);
            if (currentEventDataFlowAssert != null) {
                asserts.add(ctx.mkImplies(currentVar, currentEventDataFlowAssert));
            }
        }
        return asserts;
    }

    @Override
    public List<BoolExpr> encodeProcessRFRelation(XProcess process) {
        //throw new NotImplementedException();
        return new ArrayList<>();
    }
}