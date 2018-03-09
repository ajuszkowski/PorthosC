package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventRef;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraphBuilder;
import mousquetaires.utils.patterns.Transformer;


public class XFlowGraphUnroller implements Transformer<XFlowGraph> {

    private final int bound;
    //private Map<XEvent, Integer> referenceMap;

    public XFlowGraphUnroller(int bound) {
        this.bound = bound;
    }

    @Override
    public XFlowGraph transform(XFlowGraph graph) {
        FlowGraphUnroller<XEvent> unroller = new FlowGraphUnroller<>(graph) {
            @Override
            protected XEvent createNodeRef(XEvent node, int refId) {
                return new XEventRef(node, refId);
            }
        };
        XFlowGraphBuilder builder = new XFlowGraphBuilder(graph.processId());
        unroller.unroll(bound, builder);
        return builder.build();
    }

    //private void registerNodeRef(XEvent node, int refId) {
    //    if (!referenceMap.containsKey(node)) {
    //        newReferences.add(nodeRef);
    //        referenceMap.put(node, newReferences);
    //    }
    //    else {
    //        referenceMap.get(node).add(nodeRef);
    //    }
    //}
}
