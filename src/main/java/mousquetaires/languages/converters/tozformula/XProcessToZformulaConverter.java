package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.converters.tozformula.helpers.ZDataFlowEncoder;


public class XProcessToZformulaConverter {

    private final ZDataFlowEncoder dataFlowEncoder;

    public XProcessToZformulaConverter(ZDataFlowEncoder dataFlowEncoder) {
        this.dataFlowEncoder = dataFlowEncoder;
        //this.controlFlowEncoder = new XControlFlowEncoder(ctx, process);
        //this.dataFlowEncoder = new XDataFlowEncoder(ctx, process);
        //this.eventVariableEncoder = new XEventVariableEncoder(ctx);
        //this.operatorEncoder = new XOperatorEncoder(ctx);
    }

    /*public ZBoolFormula encode(XProcess process) {
        ZBoolConjunctionBuilder processFormula = new ZBoolConjunctionBuilder();
        XgraphLinearisedTraverser traverser = new XgraphLinearisedTraverser(process);
        XgraphHelper helper = new XgraphHelper(process);

        while (traverser.hasNext()) {
            XEvent current = traverser.next();
            ZBoolVariableGlobal currentName = ZVariableHelper.createEventVariable(current);

            // encode control-flow, 'if(c){a}else{b}' as 'not (a and b)'
            if (helper.isBranchingEvent(current)) {
                XEvent nextThen = helper.getNextThenBranchingEvent(current);
                XEvent nextElse = helper.getNextElseBranchingEvent(current);
                // add constraint 'not both then and else'
                ZBoolVariableGlobal nextThenId = ZVariableHelper.createEventVariable(nextThen);
                ZBoolVariableGlobal nextElseId = ZVariableHelper.createEventVariable(nextElse);
                ZBoolFormula branchingControlFlow = not(and(nextThenId, nextElseId));
                processFormula.addSubFormula(branchingControlFlow);
            }
            // encode control-flow, 'next -> prev'
            List<XEvent> predecessors = helper.getPredecessors(current);
            assert predecessors.size() > 0;
            for (XEvent predecessor : predecessors) {
                ZBoolVariableGlobal predecessorName = ZVariableHelper.createEventVariable(predecessor);
                ZBoolImplication controlFlowConjunct = implies(currentName, predecessorName);
                processFormula.addSubFormula(controlFlowConjunct);
            }

            // update references from all predecessors
            dataFlowEncoder.updateReferences(current, predecessors);

            // encode dataflow if needed
            ZBoolFormula dataFlowAssertion = dataFlowEncoder.encodeDataFlow(current);
            if (dataFlowAssertion != null) {
                processFormula.addSubFormula(implies(currentName, dataFlowAssertion));
            }
        }

        return processFormula.build();
    }*/


    //public Expr encode_OLD(XProcess process) {
    //    XgraphLinearisedTraverser traverser = new XgraphLinearisedTraverser(process);
    //    Queue<Transition> visitQueue = new ArrayDeque<>();
    //    Set<XEvent> visitedEvents = new HashSet<>();
    //    HashMap<XEvent, List<XEvent>> jointPoints = new HashMap<>(process.condTrueJumps.size()); // not more than number of branchings
    //
    //    ConjunctiveFormulaBuilder formulaBuilder = new ConjunctiveFormulaBuilder(ctx);
    //    XEvent entry = traverser.buildEntryEvent();
    //    visitedEvents.add(entry);
    //
    //    XEvent firstEvent = traverser.nextEpsilonEvent(entry);
    //    visitQueue.add(new Transition(entry, firstEvent));
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
    //            XEvent nextLinear = traverser.nextEpsilonEvent(currentEvent);
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
    //    BoolExpr eventExecutedExpr = createEventVariable(event);
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
