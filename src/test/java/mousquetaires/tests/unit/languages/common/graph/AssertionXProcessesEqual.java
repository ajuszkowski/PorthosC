package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.tests.unit.Assertion;
import mousquetaires.tests.unit.AssertionObjectsEqual;


public class AssertionXProcessesEqual extends AssertionGraphsEqual<XEvent, XProcess> {

    public AssertionXProcessesEqual(XProcess expected, XProcess actual) {
        super(expected, actual);
    }

    @Override
    public boolean checkSuccess() {
        Assertion idAssert = new AssertionObjectsEqual("process ID mismatch", getExpected().processId(), getActual().processId());
        if (!idAssert.checkSuccess()) {
            addErrorMessage(idAssert.getErrorMessage());
            return false;
        }
        return super.checkSuccess();
    }

}

