package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.common.graph.traverse.FlowGraphDfsTraverser;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.languages.syntax.xgraph.process.XProcessBuilder;


class XFlowGraphUnroller extends FlowGraphDfsTraverser<XEvent, XProcess> {

    // TODO: pass settings structure: bound, flags which agents to use

    XFlowGraphUnroller(XCyclicProcess graph, int unrollingBound) {
        super(graph, new XProcessBuilder(graph.getId(), graph.size()), unrollingBound);
    }
}
