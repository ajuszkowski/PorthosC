package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.common.XType;
import mousquetaires.languages.syntax.xgraph.events.computation.XAssertionEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.memories.XLocation;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.utils.exceptions.xgraph.XInterpretationError;


public class XPostludeInterpreter extends XLudeInterpreterBase {

    public XPostludeInterpreter(XProcessId processId, XMemoryManager memoryManager) {
        super(processId, memoryManager);
    }

    @Override
    public XAssertionEvent emitAssertionEvent(XBinaryComputationEvent assertion) {
        XAssertionEvent assertionEvent = new XAssertionEvent(assertion);
        processNextEvent(assertionEvent);
        return assertionEvent;
    }
}
