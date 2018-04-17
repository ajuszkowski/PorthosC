package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;


public class XCyclicProcess extends FlowGraph<XEvent> {

    private final XProcessId id;

    XCyclicProcess(XProcessId id,
                   XEntryEvent source,
                   XExitEvent sink,
                   ImmutableMap<XEvent, XEvent> trueEdges,
                   ImmutableMap<XEvent, XEvent> falseEdges) {
        super(source, sink, trueEdges, falseEdges);
        this.id = id;
    }

    public XProcessId getId() {
        return id;
    }

    @Override
    public XEntryEvent source() {
        return (XEntryEvent) super.source();
    }

    @Override
    public XExitEvent sink() {
        return (XExitEvent) super.sink();
    }
}
