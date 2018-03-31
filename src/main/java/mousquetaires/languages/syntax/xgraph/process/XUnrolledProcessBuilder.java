package mousquetaires.languages.syntax.xgraph.process;

import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;


public class XUnrolledProcessBuilder extends UnrolledFlowGraphBuilder<XEvent, XUnrolledProcess> {
    private final String processId;


    public XUnrolledProcessBuilder(String processId) {
        this.processId = processId;
    }

    @Override
    public XUnrolledProcess build() {
        finishBuilding();
        return new XUnrolledProcess(getProcessId(),
                                    getSource(),
                                    getSink(),
                                    buildEdges(true),
                                    buildEdges(false),
                                    buildReversedEdges(true),
                                    buildReversedEdges(false),
                                    buildNodesLinearised());
    }

    public String getProcessId() {
        return processId;
    }

    @Override
    public XEvent createNodeReference(XEvent node, int depth) {
        XEventInfo newInfo = node.getInfo().withUnrollingDepth(depth);
        return node.withInfo(newInfo);
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
