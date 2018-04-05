package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.syntax.xgraph.events.XEvent;


public class XProcess extends FlowGraph<XEvent> {

    private final XProcessId id;

    XProcess(XProcessId id,
             XEvent source,
             XEvent sink,
             ImmutableMap<XEvent, XEvent> trueEdges,
             ImmutableMap<XEvent, XEvent> falseEdges) {
        super(source, sink, trueEdges, falseEdges);
        this.id = id;
    }

    public XProcessId getId() {
        return id;
    }
}
