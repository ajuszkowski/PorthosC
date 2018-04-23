package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.syntax.xgraph.events.computation.XAssertionEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.utils.exceptions.xgraph.XInterpretationError;


public class XPreludeInterpreter extends XLudeInterpreterBase {

    public XPreludeInterpreter(XProcessId processId, XMemoryManager memoryManager) {
        super(processId, memoryManager);
    }

    @Override
    public XAssertionEvent processAssertion(XBinaryComputationEvent assertion) {
        throw new XInterpretationError(getIllegalOperationMessage());
    }
}
