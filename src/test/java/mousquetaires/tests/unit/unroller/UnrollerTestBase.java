package mousquetaires.tests.unit.unroller;

import mousquetaires.tests.unit.AbstractUnitTest;
import mousquetaires.tests.unit.FlowGraphComparer;
import mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraph;
import mousquetaires.tests.unit.languages.common.graph.IntTestFlowGraphUnroller;
import mousquetaires.utils.CollectionUtils;


public abstract class UnrollerTestBase extends AbstractUnitTest<IntTestFlowGraph> {

    protected void run(IntTestFlowGraph expectedUnrolled, IntTestFlowGraph actualNonUnrolled, int bound) {
        IntTestFlowGraphUnroller unroller = new IntTestFlowGraphUnroller(actualNonUnrolled, bound,
                expectedUnrolled.source(), expectedUnrolled.sink());
        unroller.doUnroll();
        IntTestFlowGraph actualUnrolled = unroller.getUnrolledGraph();
        compareResults(CollectionUtils.createIteratorFrom(expectedUnrolled),
                       CollectionUtils.createIteratorFrom(actualUnrolled));
    }

    protected abstract IntTestFlowGraph getNonUnrolledGraph();

    @Override
    protected void assertObjectsEqual(IntTestFlowGraph expected, IntTestFlowGraph actual) {
        FlowGraphComparer.assertGraphsEqual(expected, actual);
    }
}
