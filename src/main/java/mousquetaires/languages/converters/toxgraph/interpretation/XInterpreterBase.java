package mousquetaires.languages.converters.toxgraph.interpretation;


import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.events.computation.*;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XNopEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.*;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcessBuilder;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;

import javax.annotation.CheckReturnValue;

import static mousquetaires.utils.StringUtils.wrap;


abstract class XInterpreterBase implements XInterpreter {

    private final XProcessId processId;
    private final XMemoryManager memoryManager;
    protected final XCyclicProcessBuilder graphBuilder;
    private XCyclicProcess result;

    private XComputationEvent lastNonEmittedComputation;

    XInterpreterBase(XProcessId processId, XMemoryManager memoryManager) {
        this.processId = processId;
        this.memoryManager = memoryManager;
        this.graphBuilder = new XCyclicProcessBuilder(processId);
    }

    // TODO: find out where exactly should we use this (where the computation event is used, and where we can loose it)
    protected void flushComputationEvent() {
        if (lastNonEmittedComputation != null) {
            processNextEvent(lastNonEmittedComputation);
            lastNonEmittedComputation = null;
        }
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
        return memoryManager.declareLocation(name, type);
    }

    @Override
    public XRegister declareRegister(String name, Type type) {
        return memoryManager.declareRegister(name, type);
    }

    @Override
    public XRegister newTempRegister(Type type) {
        return memoryManager.newTempRegister(type);
    }

    @Override
    public XLvalueMemoryUnit declareUnresolvedUnit(String name, boolean isGlobal) {
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

    // --

    /**
     * For modelling empty statement
     */
    @Override
    public XNopEvent emitNopEvent() {
        XNopEvent event = new XNopEvent(createEventInfo());
        lastNonEmittedComputation = null;
        processNextEvent(event);
        return event;
    }

    @Override
    public XComputationEvent createComputationEvent(XUnaryOperator operator, XLocalMemoryUnit operand) {
        XUnaryComputationEvent event = new XUnaryComputationEvent(createEventInfo(), operator, operand);
        lastNonEmittedComputation = event;
        return event;
    }

    @Override
    public XComputationEvent emitComputationEvent(XUnaryOperator operator, XLocalMemoryUnit operand) {
        XComputationEvent event = createComputationEvent(operator, operand);
        lastNonEmittedComputation = null;
        processNextEvent(event);
        return event;
    }

    @Override
    public XComputationEvent createComputationEvent(XBinaryOperator operator, XLocalMemoryUnit firstOperand, XLocalMemoryUnit secondOperand) {
        XBinaryComputationEvent event = new XBinaryComputationEvent(createEventInfo(), operator, firstOperand, secondOperand);
        lastNonEmittedComputation = event;
        return event;
    }

    @Override
    public XComputationEvent emitComputationEvent(XBinaryOperator operator, XLocalMemoryUnit firstOperand, XLocalMemoryUnit secondOperand) {
        XComputationEvent event = createComputationEvent(operator, firstOperand, secondOperand);
        lastNonEmittedComputation = null;
        processNextEvent(event);
        return event;
    }

    @Override
    public XLocalMemoryEvent emitMemoryEvent(XLocalLvalueMemoryUnit destination, XLocalMemoryUnit source) {
        //emit local memory event here
        XRegisterMemoryEvent event = new XRegisterMemoryEvent(createEventInfo(), destination, source);
        lastNonEmittedComputation = null;
        processNextEvent(event);
        return event;
    }

    @Override
    public XSharedMemoryEvent emitMemoryEvent(XLocalLvalueMemoryUnit destination, XSharedMemoryUnit source) {
        XLoadMemoryEvent event = new XLoadMemoryEvent(createEventInfo(), destination, source);
        lastNonEmittedComputation = null;
        processNextEvent(event);
        return event;
    }

    @Override
    public XSharedMemoryEvent emitMemoryEvent(XSharedLvalueMemoryUnit destination, XLocalMemoryUnit source) {
        XStoreMemoryEvent event = new XStoreMemoryEvent(createEventInfo(), destination, source);
        lastNonEmittedComputation = null;
        processNextEvent(event);
        return event;
    }

    // --

    @Override
    public void finishInterpretation() {
        flushComputationEvent();
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
        if (expression != null) {
            if (expression instanceof XLocalMemoryUnit) {
                // computation events, constants here
                return (XLocalMemoryUnit) expression;
            }
            if (expression instanceof XSharedMemoryUnit) {
                return copyToLocalMemory((XSharedMemoryUnit) expression);
            }
        }
        return null;
    }

    @Override
    public XLocalLvalueMemoryUnit tryConvertToLocalLvalueOrNull(XEntity expression) {
        XLocalMemoryUnit local = tryConvertToLocalOrNull(expression);
        if (local instanceof XLocalLvalueMemoryUnit) {
            return (XLocalLvalueMemoryUnit) local;
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
    public XConstant getConstant(Object value, Type type) {
        return XConstant.create(value, type);
    }

    @Override
    public XComputationEvent evaluateMemoryUnit(XMemoryUnit memoryUnit) {
        XLocalMemoryUnit localUnit = null;
        if (memoryUnit instanceof XLocalMemoryUnit) {
            localUnit = (XLocalMemoryUnit) memoryUnit;
        }
        else if (memoryUnit instanceof XSharedMemoryUnit) {
            localUnit = tryConvertToLocalOrNull(memoryUnit);
        }
        if (localUnit == null) {
            throw new IllegalStateException("Memory unit may be either local or shared, found: "
                                                    + memoryUnit.getClass().getSimpleName());
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
