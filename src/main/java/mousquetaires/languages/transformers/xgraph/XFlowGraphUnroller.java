package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.common.graph.traverse.FlowGraphDfsTraverser;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.languages.syntax.xgraph.process.XUnrolledProcess;
import mousquetaires.languages.syntax.xgraph.process.XUnrolledProcessBuilder;


class XFlowGraphUnroller extends FlowGraphDfsTraverser<XEvent, XUnrolledProcess> {

    // TODO: pass settings structure: bound, flags which agents to use

    XFlowGraphUnroller(XProcess graph, int unrollingBound) {
        super(graph, new XUnrolledProcessBuilder(graph.processId()), unrollingBound);
    }
}
