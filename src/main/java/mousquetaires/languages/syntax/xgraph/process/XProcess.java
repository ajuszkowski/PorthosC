package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.syntax.xgraph.XProcessEntity;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;


public class XProcess extends FlowGraph<XEvent> implements XProcessEntity {

    private final String processId;

    XProcess(String processId,
             XEvent source,
             XEvent sink,
             ImmutableMap<XEvent, XEvent> trueEdges,
             ImmutableMap<XEvent, XEvent> falseEdges) {
        super(source, sink, trueEdges, falseEdges);
        this.processId = processId;
    }

    public String processId() {
        return processId;
    }


}
