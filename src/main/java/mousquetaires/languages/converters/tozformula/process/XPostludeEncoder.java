package mousquetaires.languages.converters.tozformula.process;

import com.microsoft.z3.Context;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.converters.tozformula.StaticSingleAssignmentMap;
import mousquetaires.languages.converters.tozformula.XDataflowEncoder;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.languages.syntax.zformula.ZFormulaBuilder;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.Iterator;
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
    public void encodeProcess(XProcess process, ZFormulaBuilder formulaBuilder) {

        Iterator<XEvent> nodesIterator = process.linearisedNodesIterator();
        // execute the entry event indefinitely:
        formulaBuilder.addAssert(process.source().executes(ctx));

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

            formulaBuilder.addAssert(currentEvent.accept(dataFlowEncoder));

        }
    }

    @Override
    public void encodeProcessRFRelation(XProcess process, ZFormulaBuilder formulaBuilder) {
        //throw new NotImplementedException();
    }
}
