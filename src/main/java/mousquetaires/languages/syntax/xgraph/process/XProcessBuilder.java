package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.graph.FlowGraphBuilder;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;


public class XProcessBuilder extends FlowGraphBuilder<XEvent, XProcess> {
    private final XProcessId processId;

    public XProcessBuilder(XProcessId processId) {
        this.processId = processId;
    }

    @Override
    public XProcess build() {
        finishBuilding();
        return new XProcess(getProcessId(),
                            getSource(),
                            getSink(),
                ImmutableMap.copyOf(getEdges(true)),
                ImmutableMap.copyOf(getEdges(false)));
    }

    public XProcessId getProcessId() {
        return processId;
    }


    @Override
    public void setSource(XEvent source) {
        assert source instanceof XEntryEvent : source.getClass().getSimpleName();
        super.setSource(source);
    }

    @Override
    public void setSink(XEvent sink) {
        assert sink instanceof XExitEvent : sink.getClass().getSimpleName();
        super.setSink(sink);
    }
}
