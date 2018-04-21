package mousquetaires.languages.converters.tozformula;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XSharedLvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.utils.Utils;

import java.util.*;


public class XProcess2ZformulaEncoder {

    private final Context ctx;
    private final StaticSingleAssignmentMap ssaMap;
    private final XDataflowEncoder dataFlowEncoder;

    public XProcess2ZformulaEncoder(Context ctx, StaticSingleAssignmentMap ssaMap, XDataflowEncoder dataFlowEncoder) {
        this.ctx = ctx;
        this.ssaMap = ssaMap;
        this.dataFlowEncoder = dataFlowEncoder;
    }

    // encodeCF + encodeDF
    List<BoolExpr> encodeProcess(XProcess process) {
        XProcessId pid = process.getId();

        List<BoolExpr> asserts = new ArrayList<>();

        Iterator<XEvent> nodesIterator = process.linearisedNodesIterator();

        // execute the entry event indefinitely:
        asserts.add(process.source().executes(ctx));

        while (nodesIterator.hasNext()) {
            XEvent currentEvent = nodesIterator.next();

            BoolExpr currentVar = currentEvent.executes(ctx);

            if (pid == XProcessId.PreludeProcessId) {
                // do nothing? //todo: find this out
            }
            else if (pid == XProcessId.PostludeProcessId) {
                //todo
                int a =3;
            }
            else {
                // update SSA map
                for (boolean edgeKind : FlowGraph.edgeKinds()) {
                    if (!process.hasParent(edgeKind, currentEvent)) {
                        continue;
                    }
                    Set<XEvent> parents = process.parents(edgeKind, currentEvent);
                    Set<BoolExpr> parentsVarsSet = new HashSet<>(parents.size());
                    assert parents.size() > 0 : "disconnected graph";
                    for (XEvent parent : parents) {
                        //assert visited.contains(parent) : parent + " violates topological sorting";
                        parentsVarsSet.add(parent.executes(ctx));
                        ssaMap.updateRefs(currentEvent, parent);
                    }
                    BoolExpr[] parentsVars = parentsVarsSet.toArray(new BoolExpr[0]);
                    BoolExpr eitherParentAssert = (parentsVars.length == 1)
                            ? parentsVars[0]
                            : ctx.mkOr(parentsVars);
                    asserts.add(ctx.mkImplies(currentVar, eitherParentAssert));
                }

                if (process.hasChild(true, currentEvent)) {
                    // sequential: "X ; Y"
                    // X=currentEvent, Y=child
                    XEvent child = process.child(true, currentEvent);
                    BoolExpr childVar = child.executes(ctx);
                    // assertion 'child -> or(parent1, parent2, ...)' is already added while processing parents
                    //BoolExpr childAssert = ctx.mkImplies(childVar, currentVar);
                    //asserts.add(childAssert);

                    //ssaMap.updateRefs(child, currentEvent);

                    if (process.hasChild(false, currentEvent)) {
                        // branching: "if (b) { X } else { Y }"
                        // b=currentEvent, X=child, Y=alternativeChild
                        XEvent alternativeChild = process.child(false, currentEvent);
                        BoolExpr alternativeChildVar = alternativeChild.executes(ctx);
                        // assertion 'child -> or(parent1, parent2, ...)' is already added while processing parents
                        //BoolExpr alternativeChildAssert = ctx.mkImplies(alternativeChildVar, currentVar);
                        //asserts.add(alternativeChildAssert);

                        // not together children:
                        BoolExpr notBothChildrenAssert = ctx.mkNot(ctx.mkAnd(childVar, alternativeChildVar));
                        asserts.add(notBothChildrenAssert);

                        //ssaMap.updateRefs(alternativeChild, currentEvent);

                        // encode guard:
                        assert currentEvent instanceof XComputationEvent : "only XComputationEvent can be a branching point";
                        BoolExpr guard = dataFlowEncoder.encodeGuard((XComputationEvent) currentEvent);
                        BoolExpr thenBranchGuardAssert = ctx.mkImplies(childVar, guard);
                        BoolExpr elseBranchGuardAssert = ctx.mkImplies(alternativeChildVar, guard);
                        asserts.add(thenBranchGuardAssert);
                        asserts.add(elseBranchGuardAssert);
                    }
                }
                else {
                    assert currentEvent.equals(process.sink()) :
                            "non-sink node does not have primary child: " + currentEvent;
                }

                // encode event's inner data-flow:
                BoolExpr currentEventDataFlowAssert = currentEvent.accept(dataFlowEncoder);
                if (currentEventDataFlowAssert != null) {
                    asserts.add(ctx.mkImplies(currentVar, currentEventDataFlowAssert));
                }
            }
        }

        return asserts;
    }

    List<BoolExpr> encodeProcessRFRelation(XProcess process) {
        List<BoolExpr> asserts = new ArrayList<>();
        for (XEvent loadEvent : process.getNodesExceptSource(e -> e instanceof XLoadMemoryEvent)) {
            XLoadMemoryEvent load = (XLoadMemoryEvent) loadEvent;
            XSharedLvalueMemoryUnit loadLoc = load.getSource();
            Expr loadLocVar = dataFlowEncoder.encodeMemoryUnit(loadLoc, load);
            for (XEvent storeEvent : process.getNodesExceptSource(e -> e instanceof XStoreMemoryEvent)) {
                XStoreMemoryEvent store = (XStoreMemoryEvent) storeEvent;
                XSharedLvalueMemoryUnit storeLoc = store.getDestination();
                Expr storeLocVar = dataFlowEncoder.encodeMemoryUnit(storeLoc, store);
                if (loadLoc.equals(storeLoc)) {
                    BoolExpr rfRelationVar = Utils.edge("rf", store, load, ctx);
                    asserts.add(ctx.mkImplies(rfRelationVar, ctx.mkEq(storeLocVar, loadLocVar)));
                }
            }
            // TODO: same for process.getInitEvents() ...
        }
        return asserts;
    }
}
