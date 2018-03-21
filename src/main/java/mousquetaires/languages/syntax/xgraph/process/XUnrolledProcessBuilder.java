package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.utils.CollectionUtils;

import javax.xml.stream.XMLEventWriter;
import java.util.*;


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
                                    buildLayers());
    }

    public String getProcessId() {
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
