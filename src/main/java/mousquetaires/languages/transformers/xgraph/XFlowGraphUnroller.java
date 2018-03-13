package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventRef;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraphBuilder;
import mousquetaires.utils.graphs.FlowGraphUnroller;


public class XFlowGraphUnroller {

    private final int bound;

    public XFlowGraphUnroller(int bound) {
        this.bound = bound;
    }

    public XFlowGraph unroll(XFlowGraph graph) {
        FlowGraphUnroller<XEvent> unroller = new FlowGraphUnroller<>(graph, bound) {
            @Override
            protected XEvent createNodeRef(XEvent node, int refId) {
                return new XEventRef(node, refId);
            }
        };
        XFlowGraphBuilder builder = new XFlowGraphBuilder(graph.processId());
        unroller.unrollIntoBuilder(builder);
        return builder.build();
    }
}
