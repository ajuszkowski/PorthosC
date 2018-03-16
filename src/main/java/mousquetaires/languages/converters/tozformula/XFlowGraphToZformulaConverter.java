package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.converters.tozformula.helpers.ZDataFlowEncoder;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.languages.syntax.zformula.ZBoolConjunctionBuilder;
import mousquetaires.languages.syntax.zformula.ZBoolFormula;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.HashSet;
import java.util.Set;


// TODO: generalize this class
public class XFlowGraphToZformulaConverter {

    private final ZDataFlowEncoder dataFlowEncoder;

    public XFlowGraphToZformulaConverter(ZDataFlowEncoder dataFlowEncoder) {
        this.dataFlowEncoder = dataFlowEncoder;
        //this.controlFlowEncoder = new XControlFlowEncoder(ctx, process);
        //this.dataFlowEncoder = new XDataFlowEncoder(ctx, process);
        //this.eventVariableEncoder = new XEventVariableEncoder(ctx);
        //this.operatorEncoder = new XOperatorEncoder(ctx);
    }

    public ZBoolFormula encode(XFlowGraph graph) {
        ZBoolConjunctionBuilder resultFormula = new ZBoolConjunctionBuilder();

        Set<XEvent> visited = new HashSet<>(graph.nodesCount());
        //Deque<XEvent> queue = new ArrayDeque<>(graph.edges().size());
        //queue.add(graph.source());

        throw new NotImplementedException();
        /*GraphLineariser<XEvent> lineariser = new GraphLineariser<>(graph);
        Iterable<XEvent> topologicallySortedNodes = lineariser.computeLinearisedNodes();

        visited.add(graph.source());
        visited.add(graph.sink());

        for (XEvent current : topologicallySortedNodes) {

        //while (!queue.isEmpty()) {
        //    XEvent current = queue.removeFirst();

            if (visited.contains(current)) {
                continue;
            }

            ZBoolVariable currentId = createEventVariable(current);

            // encode control-flow, 'next -> prev'
            //if (!current.equals(graph.source())) {
            Set<XEvent> parents = graph.parents(current);
            assert parents.size() > 0;
            List<ZBoolFormula> parentsIds = new ArrayList<>(parents.size());
            for (XEvent parent : parents) {
                parentsIds.add(createEventVariable(parent));
            }
            ZBoolFormula nextImpliesPreviouses = implies(currentId, or(parentsIds));
            resultFormula.addSubFormula(nextImpliesPreviouses);

            // update references from all parents
            //dataFlowEncoder.updateReferences(current, parents);

            // encode dataflow if needed AND set up ssa map
            ZBoolFormula dataFlowAssertion = current.accept(dataFlowEncoder); //TODO: do not implicitly update references while visit
            if (dataFlowAssertion != null) {
                resultFormula.addSubFormula(implies(currentId, dataFlowAssertion));
            }

            //if (!current.equals(graph.sink())) {
            //    //XEvent nextThen = graph.child(current);
            //    //queue.addLast(nextThen);
            //    //ZBoolVariable nextThenId = createEventVariable(nextThen);
            //
            //    //// encode control-flow, 'if(c){a}else{b}' as 'not (a and b)'
            //    //if (graph.hasAlternativeChild(current)) {
            //    //    XEvent nextElse = graph.alternativeChild(current);
            //    //    //queue.addLast(nextElse);
            //    //
            //    //    // add constraint 'not both then and else'
            //    //    ZBoolVariable nextElseId = createEventVariable(nextElse);
            //    //    ZBoolFormula branchingControlFlow = not(and(nextThenId, nextElseId)); //TODO: <--- THIS IS NOT CORRECT!!!
            //    //    resultFormula.addSubFormula(branchingControlFlow);
            //    //}
            //}

            visited.add(current);
        }

        return resultFormula.build();*/
    }


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
    //        BoolExpr previoiusEventId = boolConst(previousEvent.getLabel());
    //        BoolExpr currentEventId   = boolConst(currentEvent.getLabel());
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
    //            BoolExpr nextThenId = boolConst(nextThen.getLabel());
    //            BoolExpr nextElseId = boolConst(nextElse.getLabel());
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
