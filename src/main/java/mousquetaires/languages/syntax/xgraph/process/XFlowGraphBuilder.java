package mousquetaires.languages.syntax.xgraph.process;

import mousquetaires.languages.common.graph.FlowGraphBuilder;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XFakeEvent;
import mousquetaires.utils.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class XFlowGraphBuilder extends FlowGraphBuilder<XEvent, XFlowGraph> {

    public XFlowGraphBuilder(String processId) {
        super(processId);
    }

    @Override
    public XFlowGraph build() {
        finishBuilding();
        return new XFlowGraph(getProcessId(), getSource(), getSink(),
                buildEdges(), buildAltEdges(), isUnrolled());
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
        assert source instanceof XEntryEvent : source.getClass().getSimpleName();
        super.setSource(source);
    }

    @Override
    public void setSink(XEvent sink) {
        assert sink instanceof XExitEvent : sink.getClass().getSimpleName();
        super.setSink(sink);
    }

    // TODO  : inefficient now
    public void replaceEvent(XFakeEvent fakeEvent, XEvent replaceWithEvent) {
        boolean replaced    = replaceEventImpl(edges, fakeEvent, replaceWithEvent);
        boolean altReplaced = replaceEventImpl(altEdges, fakeEvent, replaceWithEvent);
        // TODO: check that the second call is not removed by java optimiser
        assert replaced || altReplaced: "could not find any predecessor for continueing event " + StringUtils.wrap(fakeEvent);
    }

    private boolean replaceEventImpl(Map<XEvent, XEvent> map, XFakeEvent fakeEvent, XEvent replacement) {
        assert fakeEvent != null : "event to be replaced is null";
        assert replacement != null : "event to be replace with is null";
        map.remove(fakeEvent);
        // iterate over all edges that point to the fakeEvent and replace it with the replacement
        List<XEvent> nodesPointingToFake = new LinkedList<>();
        for (Map.Entry<XEvent, XEvent> pair : map.entrySet()) {
            if (pair.getValue().equals(fakeEvent)) {
                nodesPointingToFake.add(pair.getKey());
            }
        }
        boolean replaced = false;
        for (XEvent src : nodesPointingToFake) {
            map.remove(src);
            map.put(src, replacement);
            replaced = true;
        }
        return replaced;
    }

    // TODO: wrap pairs map-mapReversed (for edges and reverse edges) into single class here and add methods hasEdge, replaceEvent, etc...
}
