package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraphBuilder;
import mousquetaires.utils.patterns.Transformer;


public class XFlowGraphUnroller implements Transformer<XFlowGraph> {

    private final int bound;

    public XFlowGraphUnroller(int bound) {
        this.bound = bound;
    }

    @Override
    public XFlowGraph transform(XFlowGraph process) {
        FlowGraphUnroller<XEvent> unroller = new FlowGraphUnroller<XEvent>() {
            @Override
            protected XFlowGraphBuilder createBuilder() {
                return new XFlowGraphBuilder(process.processId());
            }
            @Override
            protected XEvent createNodeRef(XEvent node, int refId) {
                return
            }
        };

        // todo: remember reference map also
        return unroller.unroll(bound);
    }
}
