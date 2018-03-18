package mousquetaires.tests.unit.unroller;

import mousquetaires.languages.common.graph.render.GraphDumper;
import mousquetaires.tests.unit.AbstractUnitTest;
import mousquetaires.tests.unit.Assertion;
import mousquetaires.tests.unit.languages.common.graph.AssertionGraphsEqual;
import mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraph;
import mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraphUnroller;
import mousquetaires.utils.CollectionUtils;
import org.junit.Assert;


public abstract class UnrollerTestBase extends AbstractUnitTest<IntTestFlowGraph> {

    protected void run(IntTestFlowGraph expectedUnrolled, IntTestFlowGraph actualNonUnrolled, int bound) {
        IntTestFlowGraphUnroller unroller = new IntTestFlowGraphUnroller(actualNonUnrolled, bound,
                expectedUnrolled.source(), expectedUnrolled.sink());
        unroller.doUnroll();
        IntTestFlowGraph actualUnrolled = unroller.getUnrolledGraph();
        Assertion assertion = compareMultipleResults(CollectionUtils.createIteratorFrom(expectedUnrolled),
                                                     CollectionUtils.createIteratorFrom(actualUnrolled));
        if (!assertion.checkSuccess()) {
            GraphDumper.tryDumpToFile(actualNonUnrolled, getTestsRoot(), "original");
            Assert.fail(assertion.getErrorMessage());
        }
    }


    protected abstract IntTestFlowGraph getNonUnrolledGraph();

    @Override
    protected Assertion compareResults(IntTestFlowGraph expected, IntTestFlowGraph actual) {
        return new AssertionGraphsEqual<>(expected, actual);
    }

    @Override
    public boolean dumpExpected(IntTestFlowGraph graph) {
        return GraphDumper.tryDumpToFile(graph,getTestsRoot(), "expected");
    }

    @Override
    public boolean dumpActual(IntTestFlowGraph graph) {
        return GraphDumper.tryDumpToFile(graph,getTestsRoot(), "actual");
    }
}
