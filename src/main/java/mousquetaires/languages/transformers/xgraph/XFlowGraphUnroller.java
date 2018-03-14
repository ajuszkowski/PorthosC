package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.utils.exceptions.NotImplementedException;


public class XFlowGraphUnroller {

    private final int bound;

    public XFlowGraphUnroller(int bound) {
        this.bound = bound;
    }

    public XFlowGraph unroll(XFlowGraph graph) {
        throw new NotImplementedException();
        /*FlowGraphUnroller<XEvent> unroller = new FlowGraphUnroller<>(graph, bound) {
            @Override
            protected XEvent createNodeRef(XEvent node, int refId) {
                return new XEventRef(node, refId);
            }
        };
        XFlowGraphBuilder builder = new XFlowGraphBuilder(graph.processId());
        unroller.unrollIntoBuilder(builder);
        return builder.build();*/
    }
}
