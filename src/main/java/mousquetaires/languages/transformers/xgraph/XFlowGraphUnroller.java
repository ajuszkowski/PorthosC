package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.common.graph.traverse.FlowGraphTraverseActor;
import mousquetaires.languages.common.graph.traverse.FlowGraphUnrollingActor;
import mousquetaires.languages.common.graph.traverse.FlowGraphUnrollingTraverser;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventRef;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraphBuilder;


public class XFlowGraphUnroller extends FlowGraphUnrollingTraverser<XEvent, XFlowGraph> {

    // TODO: pass settings structure: bound, flags which agents to use

    public XFlowGraphUnroller(XFlowGraph graph,
                              int unrollingBound,
                              FlowGraphTraverseActor<XEvent>... actors) {
        super(graph, XEventRef::new, unrollingBound, getUnrollingActor(graph.processId()), actors);
    }

    private static FlowGraphUnrollingActor<XEvent, XFlowGraph> getUnrollingActor(String processId) {
        return new FlowGraphUnrollingActor<>(new XFlowGraphBuilder(processId));
    }
}
