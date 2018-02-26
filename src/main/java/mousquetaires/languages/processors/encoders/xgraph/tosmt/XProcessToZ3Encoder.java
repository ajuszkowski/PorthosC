package mousquetaires.languages.processors.encoders.xgraph.tosmt;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import mousquetaires.languages.processors.encoders.xgraph.tosmt.helpers.ZDataFlowEncoder;
import mousquetaires.languages.processors.encoders.xgraph.tosmt.helpers.ZEventNameEncoder;
import mousquetaires.languages.syntax.smt.*;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;
import mousquetaires.languages.visitors.xgraph.XgraphHelper;
import mousquetaires.languages.visitors.xgraph.XgraphLinearisedTraverser;

import static mousquetaires.languages.syntax.smt.ZFormulaHelper.*;
import java.util.List;


public class XProcessToZ3Encoder { //extends XgraphVisitorBase<Expr> {

    private final Context ctx;
    //private final XControlFlowEncoder controlFlowEncoder;
    private final ZDataFlowEncoder dataFlowEncoder;
    //private final XOperatorEncoder operatorEncoder;

    public XProcessToZ3Encoder(Context ctx, ZDataFlowEncoder dataFlowEncoder) {
        this.ctx = ctx;
        this.dataFlowEncoder = dataFlowEncoder;
        //this.controlFlowEncoder = new XControlFlowEncoder(ctx, process);
        //this.dataFlowEncoder = new XDataFlowEncoder(ctx, process);
        //this.eventVariableEncoder = new XEventVariableEncoder(ctx);
        //this.operatorEncoder = new XOperatorEncoder(ctx);
    }

    public ZBoolFormula encode(XProcess process) {
        ZBoolFormulaConjunctionBuilder processFormula = new ZBoolFormulaConjunctionBuilder();
        XgraphLinearisedTraverser traverser = new XgraphLinearisedTraverser(process);
        XgraphHelper helper = new XgraphHelper(process);

        while (traverser.hasNext()) {
            XEvent current = traverser.next();
            ZBoolVariableGlobal currentName = getEventVariable(current);

            // encode control-flow, 'if(c){a}else{b}' as 'not (a and b)'
            if (helper.isBranchingEvent(current)) {
                XEvent nextThen = helper.getNextThenBranchingEvent(current);
                XEvent nextElse = helper.getNextElseBranchingEvent(current);
                // add constraint 'not both then and else'
                ZBoolVariableGlobal nextThenId = getEventVariable(nextThen);
                ZBoolVariableGlobal nextElseId = getEventVariable(nextElse);
                ZBoolFormula branchingControlFlow = not(and(nextThenId, nextElseId));
                processFormula.addSubFormula(branchingControlFlow);
            }
            // encode control-flow, 'next -> prev'
            List<XEvent> predecessors = helper.getPredecessors(current);
            assert predecessors.size() > 0;
            //List<BoolExpr> predecessorNamesList = new LinkedList<>();
            for (XEvent predecessor : predecessors) {
                ZBoolVariableGlobal predecessorName = getEventVariable(predecessor);
                //predecessorNamesList.add(predecessorName);
                ZBoolVariableGlobal controlFlow = implies(currentName, predecessorName);
                processFormula.addSubFormula(controlFlow);
            }

            // update references from all predecessors
            dataFlowEncoder.updateReferences(current, predecessors);

            // encode dataflow if needed
            BoolExpr dataFlowExpr = dataFlowEncoder.encodeDataFlow(current);
            if (dataFlowExpr != null) {
                processFormula.addSubFormula(implies(currentName, dataFlowExpr));
            }
        }

        return processFormula.build();
    }


    //public Expr encode_OLD(XProcess process) {
    //    XgraphLinearisedTraverser traverser = new XgraphLinearisedTraverser(process);
    //    Queue<Transition> visitQueue = new ArrayDeque<>();
    //    Set<XEvent> visitedEvents = new HashSet<>();
    //    HashMap<XEvent, List<XEvent>> jointPoints = new HashMap<>(process.thenBranchingJumpsMap.size()); // not more than number of branchings
    //
    //    ConjunctiveFormulaBuilder formulaBuilder = new ConjunctiveFormulaBuilder(ctx);
    //    XEvent entryEvent = traverser.buildEntryEvent();
    //    visitedEvents.add(entryEvent);
    //
    //    XEvent firstEvent = traverser.getNextLinearEvent(entryEvent);
    //    visitQueue.add(new Transition(entryEvent, firstEvent));
    //
    //    while (!visitQueue.isEmpty()) {
    //        Transition transition = visitQueue.remove();
    //        XEvent previousEvent = transition.from;
    //        XEvent currentEvent  = transition.to;
    //
    //        BoolExpr previoiusEventId = boolConst(previousEvent.getUniqueId());
    //        BoolExpr currentEventId   = boolConst(currentEvent.getUniqueId());
    //
    //        // control-flow:
    //        BoolExpr controlFlow = implies(currentEventId, previoiusEventId);
    //        formulaBuilder.addSubFormula(controlFlow);
    //
    //        dataFlowEncoder
    //
    //
    //        if (traverser.isBranchingEvent(currentEvent)) {
    //
    //            XEvent nextThen = traverser.getNextThenBranchingEvent(currentEvent);
    //            XEvent nextElse = traverser.getNextElseBranchingEvent(currentEvent);
    //            // add constraint 'not both then and else'
    //            BoolExpr nextThenId = boolConst(nextThen.getUniqueId());
    //            BoolExpr nextElseId = boolConst(nextElse.getUniqueId());
    //
    //            BoolExpr branchingControlFlow = not(and(nextThenId, nextElseId));
    //            formulaBuilder.addSubFormula(branchingControlFlow);
    //            // nexts:
    //            visitQueue.add(new Transition(currentEvent, nextThen));
    //            visitQueue.add(new Transition(currentEvent, nextElse));
    //        }
    //        else {
    //            // nexts:
    //            XEvent nextLinear = traverser.getNextLinearEvent(currentEvent);
    //            visitQueue.add(new Transition(currentEvent, nextLinear));
    //        }
    //
    //
    //        if (visitedEvents.contains(currentEvent)) {
    //            if (!jointPoints.containsKey(currentEvent)) {
    //                LinkedList<XEvent> previousPoints = new LinkedList<>();
    //                previousPoints.add(previousEvent);
    //                jointPoints.put(currentEvent, previousPoints);
    //            }
    //            else {
    //                jointPoints.get(currentEvent).add(previousEvent);
    //            }
    //        }
    //        visitedEvents.add(currentEvent);
    //    }
    //
    //    if (jointPoints.size() > 0) {
    //        for (Map.Entry<XEvent, List<XEvent>> entry : jointPoints.entrySet()) {
    //            XEvent toEvent = entry.getKey();
    //            List<XEvent> fromEventList = entry.getValue();
    //            for (XEvent jointPoint : fromEventList) {
    //                // encode dataflow
    //            }
    //        }
    //    }
    //
    //    return formulaBuilder.build();
    //}

    //
    //@Override
    //public IntExpr visit(XRegister event) {
    //    return visitMemoryUnit(event);
    //}
    //
    //@Override
    //public IntExpr visit(XLocation event) {
    //    return visitMemoryUnit(event);
    //}
    //
    //@Override
    //public IntExpr visit(XConstant event) {
    //    return visitMemoryUnit(event);
    //}
    //
    //@Override
    //public ArithExpr visit(XNullaryComputationEvent event) {
    //    BoolExpr eventExecutedExpr = controlFlowEncoder.encode(event);
    //    IntExpr operandExpr = visitMemoryUnit(event.getFirstOperand());
    //
    //}
    //
    //@Override
    //public Expr visit(XUnaryOperationEvent event) {
    //
    //}
    //
    //@Override
    //public BoolExpr visit(XBinaryOperationEvent event) {
    //
    //}
    //
    //@Override
    //public Expr visit(XStoreMemoryEvent event) {
    //    return visitMemoryEvent(event);
    //}
    //
    //@Override
    //public Expr visit(XLoadMemoryEvent event) {
    //    return visitMemoryEvent(event);
    //}
    //
    ////--
    //
    //private IntExpr visitMemoryUnit(XMemoryUnit event) {
    //    return memoryUnitEncoder.encode(event);
    //}
    //
    //private Expr visitMemoryEvent(XMemoryEvent event) {
    //    Expr source = memoryUnitEncoder.encode(event.getSource());
    //    Expr destination = memoryUnitEncoder.encode(event.getDestination());
    //    BoolExpr eventExecutedExpr = getEventVariable(event);
    //    return ctx.mkImplies(eventExecutedExpr, ctx.mkEq(source, destination));
    //}

    //class Transition {
    //    final XEvent from;
    //    final XEvent to;
    //    Transition(XEvent from, XEvent to) {
    //        this.from = from;
    //        this.to = to;
    //    }
    //}
}
