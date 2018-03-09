package mousquetaires.tests.unit.unroller;

import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.languages.transformers.xgraph.XFlowGraphUnroller;
import mousquetaires.tests.unit.AbstractUnitTest;
import mousquetaires.tests.unit.FlowGraphComparer;
import mousquetaires.utils.CollectionUtils;
import org.junit.Assert;


public class UnrollerTestBase extends AbstractUnitTest<XFlowGraph> {

    protected void run(XFlowGraph expectedUnrolled, XFlowGraph actualNonUnrolled, int bound) {
        XFlowGraph actualUnrolled = new XFlowGraphUnroller(bound).transform(actualNonUnrolled);
        compareResults(CollectionUtils.createIteratorFrom(expectedUnrolled),
                       CollectionUtils.createIteratorFrom(actualUnrolled));
    }

    //protected FlowGraph<TestNode> unrollGraph(FlowGraph<TestNode> graph, int bound) {
    //    ActualTestFlowGraphBuilder builder = new ActualTestFlowGraphBuilder();
    //    FlowGraphUnroller<TestNode> unroller = new FlowGraphUnroller<>(graph) {
    //        @Override
    //        protected TestNode createNodeRef(TestNode node, int refId) {
    //            return builder.nodeToRef(node);
    //        }
    //    };
    //    unroller.unroll(bound, builder);
    //    return builder.build();
    //}
    //@Override
    //protected void assertObjectsEqual(FlowGraph<TestNode> expected, FlowGraph<TestNode> actual) {
    //    FlowGraphComparer.assertGraphsEqual(expected, actual);
    //}

    @Override
    protected void assertObjectsEqual(XFlowGraph expected, XFlowGraph actual) {
        Assert.assertEquals("process ID mismatch", expected.processId(), actual.processId());
        FlowGraphComparer.assertGraphsEqual(expected, actual);
    }

}
