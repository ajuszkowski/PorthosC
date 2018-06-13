package porthosc.tests.unit.languages.common.graph;

import porthosc.languages.syntax.xgraph.events.XEvent;
import porthosc.languages.syntax.xgraph.process.XCyclicProcess;
import porthosc.tests.unit.Assertion;
import porthosc.tests.unit.AssertionObjectsEqual;


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

