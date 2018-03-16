package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.tests.unit.Assertion;
import mousquetaires.tests.unit.AssertionObjectsEqual;


public class AssertionXGraphsEqual extends AssertionGraphsEqual<XEvent, XFlowGraph> {

    public AssertionXGraphsEqual(XFlowGraph expected, XFlowGraph actual) {
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

