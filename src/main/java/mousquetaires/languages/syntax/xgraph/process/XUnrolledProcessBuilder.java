package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.utils.CollectionUtils;

import java.util.*;


public class XUnrolledProcessBuilder
        extends XProcessBuilderBase<XUnrolledProcess>
        implements UnrolledFlowGraphBuilder<XEvent, XUnrolledProcess> {

    private Deque<XEvent> linearisedQueue;
    private Map<XEvent, Set<XEvent>> predecessorsMap;

    public XUnrolledProcessBuilder(String processId) {
        super(processId);
        this.linearisedQueue = new ArrayDeque<>();
        this.predecessorsMap = new HashMap<>();
    }

    @Override
    public XUnrolledProcess build() {
        return new XUnrolledProcess(getProcessId(), getSource(), getSink(),
                ImmutableMap.copyOf(getEdges(true)),
                ImmutableMap.copyOf(getEdges(false)),
                ImmutableList.copyOf(linearisedQueue),
                CollectionUtils.buildMapOfSets(predecessorsMap));
    }

    @Override
    public void addNextLinearisedNode(XEvent event) {
        if (linearisedQueue.size() == 0) {
            linearisedQueue.add(event);
        }
        if (!linearisedQueue.contains(event)) { // no loops => safe (check is for not to add multiple [EXIT] nodes)
            linearisedQueue.addFirst(event);
        }
    }

    @Override
    public void addPredecessorPair(XEvent child, XEvent parent) {
        if (!predecessorsMap.containsKey(child)) {
            predecessorsMap.put(child, new HashSet<>());
        }
        predecessorsMap.get(child).add(parent);
    }
}
