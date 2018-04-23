package mousquetaires.languages.converters.tozformula.process;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import dartagnan.program.Location;
import dartagnan.program.Register;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.converters.tozformula.StaticSingleAssignmentMap;
import mousquetaires.languages.converters.tozformula.XDataflowEncoder;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class XPostludeEncoder implements XProcessEncoder {
    private final Context ctx;
    private final StaticSingleAssignmentMap ssaMap;
    private final XDataflowEncoder dataFlowEncoder;

    public XPostludeEncoder(Context ctx, StaticSingleAssignmentMap ssaMap, XDataflowEncoder dataFlowEncoder) {
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

            asserts.add(currentEvent.accept(dataFlowEncoder));

        }
        return asserts;
    }

    @Override
    public List<BoolExpr> encodeProcessRFRelation(XProcess process) {
        //throw new NotImplementedException();
        return new ArrayList<>();
    }
}
