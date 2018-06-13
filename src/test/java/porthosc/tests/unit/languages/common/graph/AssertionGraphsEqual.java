package porthosc.tests.unit.languages.common.graph;

import porthosc.languages.common.graph.FlowGraph;
import porthosc.languages.common.graph.FlowGraphNode;
import porthosc.tests.unit.Assertion;
import porthosc.tests.unit.AssertionMapContains;
import porthosc.tests.unit.AssertionObjectsEqual;
import porthosc.tests.unit.AssertionSuccess;
import porthosc.utils.StringUtils;

import java.util.Map;


public class AssertionGraphsEqual<N extends FlowGraphNode, G extends FlowGraph<N>> extends Assertion {

    private final G expected;
    private final G actual;

    public AssertionGraphsEqual(G expected, G actual) {
        super("comparing graphs");
        this.expected = expected;
        this.actual = actual;
    }

    public G getExpected() {
        return expected;
    }

    public G getActual() {
        return actual;
    }

    @Override
    public String getErrorMessage() {
        return super.getErrorMessage() + "\n\n" +
                "Expected=" + StringUtils.wrap(getExpected()) + "\n\n" +
                "Actual=" + StringUtils.wrap(getActual());
    }

    @Override
    public boolean checkSuccess() {
        Assertion sourcesAssert = new AssertionObjectsEqual("entry events do not match", expected.source(), actual.source());
        if (!sourcesAssert.checkSuccess()) {
            addErrorMessage(sourcesAssert.getErrorMessage());
            return false;
        }
        Assertion sinksAssert = new AssertionObjectsEqual("exit events do not match", expected.sink(), actual.sink());
        if (!sinksAssert.checkSuccess()) {
            addErrorMessage(sinksAssert.getErrorMessage());
            return false;
        }
        Assertion thenComparison = compareMaps("comparing then-edges", expected.getEdges(true), actual.getEdges(true));
        if (!thenComparison.checkSuccess()) {
            addErrorMessage(thenComparison.getErrorMessage());
            return false;
        }
        Assertion elseComparison = compareMaps("comparing else-edges", expected.getEdges(false), actual.getEdges(false));
        if (!elseComparison.checkSuccess()) {
            addErrorMessage(elseComparison.getErrorMessage());
            return false;
        }
        return true;
    }

    private static <T extends FlowGraphNode> Assertion compareMaps(String info,
                                                                       Map<? extends T, ? extends T> expected,
                                                                       Map<? extends T, ? extends T> actualMap) {
        Assertion sizeAssert = new AssertionObjectsEqual(info + ": maps sizes mismatch", expected.size(), actualMap.size());
        if (!sizeAssert.checkSuccess()) {
            return sizeAssert;
        }

        for (Map.Entry<? extends T, ? extends T> entry : expected.entrySet()) {
            T expectedKey = entry.getKey(), expectedValue = entry.getValue();
            Assertion containsAssert = new AssertionMapContains<>(info + ": actual map does not contain expected key-value pair",
                    actualMap, expectedKey, expectedValue);
            if (!containsAssert.checkSuccess()) {
                return containsAssert;
            }
        }
        return AssertionSuccess.instance();
    }
}

