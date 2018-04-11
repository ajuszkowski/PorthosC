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
    public XEntryEvent getSource() {
        return (XEntryEvent) super.getSource();
    }

    @Override
    public XExitEvent getSink() {
        return (XExitEvent) super.getSink();
    }

    @Override
    public void setSource(XEvent source) {
        if (!(source instanceof XEntryEvent)) {
            throw new IllegalArgumentException();
        }
        super.setSource(source);
    }

    @Override
    public void setSink(XEvent sink) {
        if (!(sink instanceof XExitEvent)) {
            throw new IllegalArgumentException();
        }
        super.setSink(sink);
    }
}
