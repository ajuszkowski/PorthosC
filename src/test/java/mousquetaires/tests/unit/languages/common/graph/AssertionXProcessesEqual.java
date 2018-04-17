package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;
import mousquetaires.tests.unit.Assertion;
import mousquetaires.tests.unit.AssertionObjectsEqual;


public class AssertionXProcessesEqual extends AssertionGraphsEqual<XEvent, XCyclicProcess> {

    public AssertionXProcessesEqual(XCyclicProcess expected, XCyclicProcess actual) {
        super(expected, actual);
    }

    @Override
    public boolean checkSuccess() {
        Assertion idAssert = new AssertionObjectsEqual("process ID mismatch", getExpected().getId(), getActual().getId());
        if (!idAssert.checkSuccess()) {
            addErrorMessage(idAssert.getErrorMessage());
            return false;
        }
        return super.checkSuccess();
    }

}

