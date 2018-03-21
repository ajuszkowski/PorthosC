package mousquetaires.tests.unit.unroller;

import mousquetaires.languages.common.graph.render.GraphDumper;
import mousquetaires.tests.unit.AbstractUnitTest;
import mousquetaires.tests.unit.Assertion;
import mousquetaires.tests.unit.languages.common.graph.AssertionGraphsEqual;
import mousquetaires.tests.unit.languages.common.graph.IntFlowGraph;
import mousquetaires.tests.unit.languages.common.graph.IntFlowGraphUnroller;
import mousquetaires.tests.unit.languages.common.graph.UnrolledIntFlowGraph;
import mousquetaires.utils.CollectionUtils;
import org.junit.Assert;


public abstract class UnrollerTestBase extends AbstractUnitTest<UnrolledIntFlowGraph> {

    protected void run(UnrolledIntFlowGraph expectedUnrolled, IntFlowGraph actualNonUnrolled, int bound) {
        IntFlowGraphUnroller unroller = new IntFlowGraphUnroller(actualNonUnrolled, bound,
                expectedUnrolled.source(), expectedUnrolled.sink());
        unroller.doUnroll();
        UnrolledIntFlowGraph actualUnrolled = unroller.getProcessedGraph();
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
