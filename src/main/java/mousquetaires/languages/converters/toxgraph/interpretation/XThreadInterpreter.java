package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.converters.toxgraph.hooks.HookManager;
import mousquetaires.languages.converters.toxgraph.hooks.InterceptionAction;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XAssertionEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.exceptions.xgraph.XInterpretationError;
import mousquetaires.utils.exceptions.xgraph.XInterpreterUsageError;

import javax.annotation.Nullable;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

import static mousquetaires.utils.StringUtils.wrap;


class XThreadInterpreter extends XInterpreterBase {

    private final HookManager hookManager;

    // todo: add add/put methods with non-null checks
    private final Stack<XBlockContext> contextStack;
    private final Deque<XBlockContext> almostReadyContexts;
    private final Deque<XBlockContext> readyContexts;

    XThreadInterpreter(XProcessId processId, XMemoryManager memoryManager, HookManager hookManager) {
        super(processId, memoryManager); //todo: non-uniqueness case
        contextStack = new Stack<>();
        readyContexts = new LinkedList<>();
        almostReadyContexts = new LinkedList<>();

        XBlockContext linearContext = new XBlockContext(BlockKind.Sequential);
        linearContext.state = XBlockContext.State.WaitingNextLinearEvent;
        this.contextStack.push(linearContext);

        this.hookManager = hookManager;
    }

    // --

    @Override
    public void finishInterpretation() {
        //todo: verify
        super.finishInterpretation();
        assert contextStack.size() == 1 : contextStack.size(); //linear entry context only
        assert readyContexts.isEmpty() : readyContexts.size();
        assert almostReadyContexts.isEmpty() : almostReadyContexts.size();
    }

    @Override
    public XBarrierEvent emitBarrierEvent(XBarrierEvent.Kind kind) {
        //flushPostfixOperationsCache();
        XBarrierEvent event = kind.create(createEventInfo());
        processNextEvent(event);
        return event;
    }

    @Override
    public XJumpEvent emitJumpEvent() {
        //flushPostfixOperationsCache();
        XJumpEvent event = new XJumpEvent(createEventInfo());
        processNextEvent(event);
        return event;
    }

    @Override
    protected void processNextEvent(XEvent nextEvent) {
        preProcessEvent(nextEvent);

        boolean alreadySetEdgeToNextEvent = processReadyContexts(nextEvent);

        assert !contextStack.empty();
        for (int i = contextStack.size() - 1; i >= 0; i--) { //NonlinearBlock context : contextStack) {
            XBlockContext context = contextStack.get(i);

            switch (context.state) {
                case WaitingNextLinearEvent: {
                    if (!alreadySetEdgeToNextEvent) {
                        if (previousEvent != null) {
                            graphBuilder.addEdge(true,previousEvent, nextEvent);
                        }
                        alreadySetEdgeToNextEvent = true;
                    }
                }
                break;

                //case Idle: {
                //    // do nothing
                //}
                //break;

                case WaitingAdditionalCommand: {
                    throw new IllegalStateException("waiting for an additional command before processing next event: " + nextEvent);
                }
                //break;

                //case WaitingFirstConditionEvent: {
                //    context.setEntryEvent(nextEvent);//already initialised above
                //}
                //break;

                case WaitingFirstSubBlockEvent: { //this case of stateStack should be on stack
                    assert nextEvent != context.conditionEvent;
                    switch (context.currentBranchKind) {
                        case Then:
                            // todo: do all nonlinear edge settings from ready hashset
                            context.firstThenBranchEvent = nextEvent;
                            break;
                        case Else:
                            context.firstElseBranchEvent = nextEvent;
                            break;
                    }
                    context.setState(XBlockContext.State.WaitingNextLinearEvent);
                    alreadySetEdgeToNextEvent = true; //delayed edge set (in traversing ready-contexts)
                }
                break;

                case Idle: {
                    // do nothing
                }
                break;

                default:
                    throw new XInterpreterUsageError("Received new event while in invalid stateStack: " + context.state.name());
            }
        }

        postProcessEvent(nextEvent);
    }

    // TODO: should be in preProcessEvent()
    private boolean processReadyContexts(XEvent nextEvent) {
        boolean alreadySetEdgeToNextEvent = false;
        if (!readyContexts.isEmpty()) {

            for (XBlockContext context : readyContexts) {

                if (context.firstThenBranchEvent != null) {
                    switch (context.kind) {
                        case Sequential:
                            assert false : "no then-events are allowed for linear statements";
                            break;
                        case Branching:
                            graphBuilder.addEdge(true, context.conditionEvent, context.firstThenBranchEvent);
                            break;
                        case Loop:
                            graphBuilder.addEdge(true, context.conditionEvent, context.firstThenBranchEvent);
                            graphBuilder.addEdge(false, context.conditionEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true;
                            break;
                    }
                }
                else {
                    switch (context.kind) {
                        case Sequential:
                            assert false : "no then-events are allowed for linear statements";
                            break;
                        case Branching:
                        case Loop:
                            assert false : "every branching statement must have at least one then-event (NOP if none)";
                            break;
                    }
                }
                if (context.lastThenBranchEvent != null) {
                    switch (context.kind) {
                        case Sequential:
                            assert false : "no then-events are allowed for linear statements";
                            break;
                        case Branching:
                            graphBuilder.addEdge(true, context.lastThenBranchEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true;
                            break;
                        case Loop:
                            graphBuilder.addEdge(true, context.lastThenBranchEvent, context.entryEvent);
                            break;
                    }
                }

                if (context.firstElseBranchEvent != null) {
                    switch (context.kind) {
                        case Sequential:
                            assert false : "no else-events are allowed for linear statements";
                            break;
                        case Branching:
                            graphBuilder.addEdge(false, context.conditionEvent, context.firstElseBranchEvent);
                            break;
                        case Loop:
                            assert false : "no else-events are allowed for loop staements";
                            break;
                    }
                }
                else {
                    switch (context.kind) {
                        case Sequential:
                            assert false;
                            break;
                        case Branching:
                            assert false : "every branching statement must have at least one then-event (NOP if none)";
                            break;
                        case Loop:
                            break;
                    }
                }
                if (context.lastElseBranchEvent != null) {
                    switch (context.kind) {
                        case Sequential:
                            assert false : "no else-events are allowed for linear statements";
                            break;
                        case Branching:
                            graphBuilder.addEdge(true, context.lastElseBranchEvent, nextEvent);
                            alreadySetEdgeToNextEvent = true;
                            break;
                        case Loop:
                            assert false : "no else-events are allowed for loop staements";
                            break;
                    }
                }
            }
            readyContexts.clear();
        }
        return alreadySetEdgeToNextEvent;
    }

    // -- BRANCHING + LOOPS --------------------------------------------------------------------------------------------

    @Override
    public void startBlockDefinition(BlockKind blockKind) {
        contextStack.push(new XBlockContext(blockKind));
    }

    @Override
    public void startBlockConditionDefinition() {
        XBlockContext context = contextStack.peek();
        assert context.state == XBlockContext.State.WaitingAdditionalCommand : context.state.name();
        context.setState(XBlockContext.State.Idle);
    }

    @Override
    public void finishBlockConditionDefinition(XComputationEvent conditionEvent) {
        XBlockContext context = contextStack.peek();
        // TODO: send here Branching event! -- ? maybe we don't need them, as it's implemented now
        processNextEvent(conditionEvent);
        context.setConditionEvent(conditionEvent);
        context.setState(XBlockContext.State.WaitingAdditionalCommand);
        previousEvent = null;
    }

    @Override
    public void startBlockBranchDefinition(BranchKind branchKind) {
        XBlockContext context = contextStack.peek();
        assert context.state == XBlockContext.State.WaitingAdditionalCommand : context.state.name();
        context.setState(XBlockContext.State.WaitingFirstSubBlockEvent);
        switch (branchKind) {
            case Then: {
                context.currentBranchKind = BranchKind.Then;
            }
            break;
            case Else: {
                context.currentBranchKind = BranchKind.Else;
                if (!readyContexts.isEmpty()) {
                    almostReadyContexts.addAll(readyContexts);
                    readyContexts.clear();
                }
            }
            break;
        }
    }

    @Override
    public void finishBlockBranchDefinition() {
        XBlockContext context = contextStack.peek();
        assert context.currentBranchKind != null;
        if (previousEvent != context.conditionEvent) {
            switch (context.currentBranchKind) {
                case Then:
                    assert context.lastThenBranchEvent == null;
                    context.lastThenBranchEvent = previousEvent;
                    if (context.firstThenBranchEvent == null) { //TODO: kostyl?
                        context.firstThenBranchEvent = previousEvent;
                    }
                    break;
                case Else:
                    assert context.lastElseBranchEvent == null;
                    context.lastElseBranchEvent = previousEvent;
                    if (context.firstElseBranchEvent == null) { //TODO: kostyl?
                        context.firstElseBranchEvent = previousEvent;
                    }
                    break;
            }
        }
        context.currentBranchKind = null;
        context.setState(XBlockContext.State.WaitingAdditionalCommand);
        previousEvent = null; //todo: check
    }


    @Override
    public void finishNonlinearBlockDefinition() {
        XBlockContext context = contextStack.pop();
        context.currentBranchKind = null;
        context.setState(XBlockContext.State.Idle);
        almostReadyContexts.addFirst(context);
        previousEvent = null; //not to set too many linear jumps: e.g. `if (a) { while(b) do1(); } do2();`

        if (!almostReadyContexts.isEmpty()) {
            readyContexts.addAll(almostReadyContexts);
            almostReadyContexts.clear();
        }
        previousEvent = null;
    }

    @Override
    public void processJumpStatement(JumpKind jumpKind) {
        //flushPostfixOperationsCache();
        XJumpEvent jumpEvent = emitJumpEvent();
        XBlockContext context = currentNearestLoopContext(); //context should peeked after the jump event was emitted
        context.addJumpEvent(jumpKind, jumpEvent);
        context.setState(XBlockContext.State.Idle);
    }

    @Override
    public XAssertionEvent processAssertion(XBinaryComputationEvent assertion) {
        throw new XInterpretationError(getIllegalOperationMessage());
    }
// -- METHOD CALLS -------------------------------------------------------------------------------------------------

    // TODO: signature instead of just name
    // TODO: arguments: write all shared to registers and set up control-flow binding
    @Override
    public XEntity processMethodCall(String methodName, @Nullable XMemoryUnit receiver, XMemoryUnit... arguments) {
        //flushPostfixOperationsCache();
        InterceptionAction intercepted = hookManager.tryInterceptInvocation(methodName);
        if (intercepted != null) {
            XEntity res = intercepted.execute(receiver, arguments);
            // TODO: After we set up finding type processors by signature, not by name, we should make a restriction that their lambdas cannot return null!
            if (res != null) {
                return res;
            }
        }
        throw new NotImplementedException("Method call " + wrap(methodName) + " was not recognised" +
                                                      ", however, the method call binding is not implemented yet");
    }

    // =================================================================================================================

    private XBlockContext currentNearestLoopContext() {
        for (int i = contextStack.size() - 1; i >= 0; --i) {
            XBlockContext context = contextStack.get(i);
            if (context.kind == BlockKind.Loop) {
                return context;
            }
        }
        throw new XInterpreterUsageError("Not found any loop contexts");
    }
}
