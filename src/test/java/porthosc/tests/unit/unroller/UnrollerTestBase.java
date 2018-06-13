package porthosc.tests.unit.unroller;

import porthosc.languages.common.graph.render.GraphDumper;
import porthosc.tests.unit.AbstractUnitTest;
import porthosc.tests.unit.Assertion;
import porthosc.tests.unit.languages.common.graph.AssertionGraphsEqual;
import porthosc.tests.unit.languages.common.graph.IntFlowGraph;
import porthosc.tests.unit.languages.common.graph.IntFlowGraphUnroller;
import porthosc.tests.unit.languages.common.graph.UnrolledIntFlowGraph;
import porthosc.utils.CollectionUtils;
import org.junit.Assert;


public abstract class UnrollerTestBase extends AbstractUnitTest<UnrolledIntFlowGraph> {

    protected void run(UnrolledIntFlowGraph expectedUnrolled, IntFlowGraph actualNonUnrolled, int bound) {
        IntFlowGraphUnroller unroller = new IntFlowGraphUnroller(actualNonUnrolled, bound,
                expectedUnrolled.source(), expectedUnrolled.sink());
        unroller.doUnroll();
        UnrolledIntFlowGraph actualUnrolled = unroller.getUnrolledGraph();
        Assertion assertion = compareMultipleResults(CollectionUtils.createIteratorFrom(expectedUnrolled),
                                                     CollectionUtils.createIteratorFrom(actualUnrolled));
        if (!assertion.checkSuccess()) {
            GraphDumper.tryDumpToFile(actualNonUnrolled, getTestsRoot(), "original");
            Assert.fail(assertion.getErrorMessage());
        }
    }


    protected abstract IntFlowGraph getOriginalGraph();

    @Override
    protected Assertion getComparingAssertion(UnrolledIntFlowGraph expected, UnrolledIntFlowGraph actual) {
        return new AssertionGraphsEqual<>(expected, actual);
    }

    @Override
    public boolean dumpExpected(UnrolledIntFlowGraph graph) {
        return GraphDumper.tryDumpToFile(graph,getTestsRoot(), "expected");
    }

    @Override
    public boolean dumpActual(UnrolledIntFlowGraph graph) {
        return GraphDumper.tryDumpToFile(graph,getTestsRoot(), "actual");
    }
}
