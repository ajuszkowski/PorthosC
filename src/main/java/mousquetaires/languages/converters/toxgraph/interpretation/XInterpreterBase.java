package mousquetaires.languages.converters.toxgraph.interpretation;


import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.events.computation.*;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.controlflow.XNopEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.*;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcessBuilder;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;

import static mousquetaires.utils.StringUtils.wrap;


abstract class XInterpreterBase implements XInterpreter {

    private final XProcessId processId;
    private final XMemoryManager memoryManager;
    protected final XCyclicProcessBuilder graphBuilder;
    private XCyclicProcess result;

    protected XEvent previousEvent;

    XInterpreterBase(XProcessId processId, XMemoryManager memoryManager) {
        this.processId = processId;
        this.memoryManager = memoryManager;
        this.graphBuilder = new XCyclicProcessBuilder(processId);
    }

    protected void preProcessEvent(XEvent nextEvent) {
        assert nextEvent != null;
        // want more? overload!
    }

    protected void postProcessEvent(XEvent nextEvent) {
        previousEvent = nextEvent;
    }

    protected abstract void processNextEvent(XEvent nextEvent);

    // --

    @Override
    public XProcessId getProcessId() {
        return processId;
    }

    @Override
    public XEntryEvent emitEntryEvent() {
        XEntryEvent entryEvent = new XEntryEvent(createEventInfo());
        graphBuilder.setSource(entryEvent);
        processNextEvent(entryEvent);
        return entryEvent;
    }

    @Override
    public XExitEvent emitExitEvent() {
        XExitEvent exitEvent = new XExitEvent(createEventInfo());
        processNextEvent(exitEvent);
        graphBuilder.setSink(exitEvent);
        return exitEvent;
    }

    // --

    @Override
    public XLocation declareLocation(String name, Type type) {
        //XLocation location = memoryManager.declareLocation(name, type);
        //emitDeclarationEvent(location);
        //return location;
        return memoryManager.declareLocation(name, type);
    }

    @Override
    public XRegister declareRegister(String name, Type type) {
        //XRegister register = memoryManager.declareRegister(name, type);
        //emitDeclarationEvent(register);
        //return register;
        return memoryManager.declareRegister(name, type);
    }

    @Override
    public XRegister declareTempRegister(Type type) {
        //XRegister register = memoryManager.newTempRegister(type);
        //emitDeclarationEvent(register);
        //return register;
        return memoryManager.newTempRegister(type);
    }

    @Override
    public XLvalueMemoryUnit declareUnresolvedUnit(String name, boolean isGlobal) {
        //XLvalueMemoryUnit unit = memoryManager.declareUnresolvedUnit(name, isGlobal);
        //emitDeclarationEvent(unit);
        //return unit;
        return memoryManager.declareUnresolvedUnit(name, isGlobal);
    }

    @Override
    public XLvalueMemoryUnit getDeclaredUnitOrNull(String name) {
        return memoryManager.getDeclaredUnitOrNull(name);
    }

    @Override
    public XRegister getDeclaredRegister(String name, XProcessId processId) {
        return memoryManager.getDeclaredRegister(name, processId);
    }

    //public XDeclarationEvent emitDeclarationEvent(XMemoryUnit memoryUnit) {
    //    XDeclarationEvent event = new XDeclarationEvent(createEventInfo(), memoryUnit);
    //    processNextEvent(event);
    //    return event;
    //}

    // --

    /**
     * For modelling empty statement
     */
    @Override
    public XNopEvent emitNopEvent() {
        XNopEvent event = new XNopEvent(createEventInfo());
        processNextEvent(event);
        return event;
    }

    @Override
    public XLocalMemoryEvent emitMemoryEvent(XLocalLvalueMemoryUnit destination, XLocalMemoryUnit source) {
        //emit local memory event here
        XRegisterMemoryEvent event = new XRegisterMemoryEvent(createEventInfo(), destination, source);
        processNextEvent(event);
        return event;
    }

    @Override
    public XSharedMemoryEvent emitMemoryEvent(XLocalLvalueMemoryUnit destination, XSharedMemoryUnit source) {
        XLoadMemoryEvent event = new XLoadMemoryEvent(createEventInfo(), destination, source);
        processNextEvent(event);
        return event;
    }

    @Override
    public XSharedMemoryEvent emitMemoryEvent(XSharedLvalueMemoryUnit destination, XLocalMemoryUnit source) {
        XStoreMemoryEvent event = new XStoreMemoryEvent(createEventInfo(), destination, source);
        processNextEvent(event);
        return event;
    }

    // --

    //@Override
    //public XComputationEvent emitSimpleComputationEvent(XUnaryOperator operator, XLocalMemoryUnit operand) {
    //    XUnaryComputationEvent event = new XUnaryComputationEvent(createEventInfo(), operator, operand);
    //    processNextEvent(event);
    //    return event;
    //}
    //
    //@Override
    //public XComputationEvent emitSimpleComputationEvent(XBinaryOperator operator, XLocalMemoryUnit firstOperand, XLocalMemoryUnit secondOperand) {
    //    XBinaryComputationEvent event = new XBinaryComputationEvent(createEventInfo(), operator, firstOperand, secondOperand);
    //    processNextEvent(event);
    //    return event;
    //}

    @Override
    public XComputationEvent createComputationEvent(XUnaryOperator operator, XLocalMemoryUnit operand) {
        return new XUnaryComputationEvent(createEventInfo(), operator, operand);
    }

    @Override
    public XComputationEvent createComputationEvent(XBinaryOperator operator, XLocalMemoryUnit firstOperand, XLocalMemoryUnit secondOperand) {
        return new XBinaryComputationEvent(createEventInfo(), operator, firstOperand, secondOperand);
    }

    // --

    @Override
    public void finishInterpretation() {
        emitExitEvent();
        result = graphBuilder.build();
    }

    @Override
    public final XCyclicProcess getResult() {
        if (result == null) {
            finishInterpretation();
            assert result != null;
        }
        return result;
    }

    @Override
    public XLocalMemoryUnit tryConvertToLocalOrNull(XEntity expression) {
        if (expression instanceof XLocalMemoryUnit) {
            // computation events, constants here
            return (XLocalMemoryUnit) expression;
        }
        if (expression instanceof XSharedMemoryUnit) {
            return copyToLocalMemory((XSharedMemoryUnit) expression);
        }
        return null;
    }

    //public XLocalMemoryUnit copyToLocalMemoryIfNecessary(XMemoryUnit memoryUnit) {
    //    if (memoryUnit instanceof XLocation) {
    //        return copyToLocalMemory((XLocation) memoryUnit);
    //    }
    //    else if (memoryUnit instanceof XLocalMemoryUnit) { // also here: XComputationEvent
    //        return (XLocalMemoryUnit) memoryUnit;
    //    }
    //    throw new XInterpretationError("Illegal attempt to write to the local memory a memory unit of type "
    //            + memoryUnit.getClass().getSimpleName());
    //}

    @Override
    public XRegister copyToLocalMemory(XSharedMemoryUnit shared) {
        XRegister tempLocal = memoryManager.newTempRegister(shared.getType());
        emitMemoryEvent(tempLocal, shared);
        return tempLocal;
    }

    // --

    @Override
    public XComputationEvent tryEvaluateComputation(XEntity entity) {
        XLocalMemoryUnit localUnit = null;
        if (entity instanceof XLocalMemoryUnit) {
            if (entity instanceof XComputationEvent) {
                return (XComputationEvent) entity;
            }
            localUnit = (XLocalMemoryUnit) entity;
        }
        else if (entity instanceof XSharedMemoryUnit) {
            localUnit = tryConvertToLocalOrNull(entity);
        }
        if (localUnit == null) {
            throw new IllegalStateException("Could not convert x-entity to local memory unit: " + wrap(entity));
        }
        return createComputationEvent(XUnaryOperator.NoOperation, localUnit);
    }

    protected XEventInfo createEventInfo() {
        return new XEventInfo(getProcessId());
    }

    protected String getIllegalOperationMessage() {
        return "Illegal operation for " + getClass().getSimpleName() + " interpreter";
    }
}
