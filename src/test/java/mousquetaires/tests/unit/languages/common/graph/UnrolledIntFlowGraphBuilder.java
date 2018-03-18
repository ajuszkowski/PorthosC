package mousquetaires.tests.unit.languages.common.graph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;
import mousquetaires.utils.CollectionUtils;

import java.util.*;


public class UnrolledIntFlowGraphBuilder
        extends TestFlowGraphBuilderBase<IntGraphNode, UnrolledIntFlowGraph>
        implements UnrolledFlowGraphBuilder<IntGraphNode, UnrolledIntFlowGraph> {

    private Deque<IntGraphNode> linearisedQueue = new ArrayDeque<>();
    private Map<IntGraphNode, Set<IntGraphNode>> predecessorsMap = new HashMap<>();

    
    public UnrolledIntFlowGraphBuilder(IntGraphNode source, IntGraphNode sink) {
        super(source, sink);
    }

    @Override
    public UnrolledIntFlowGraph build() {
        finishBuilding();
        return new UnrolledIntFlowGraph(getSource(), getSink(),
                buildEdges(true),
                buildEdges(false),
                ImmutableList.copyOf(linearisedQueue),
                CollectionUtils.buildMapOfSets(predecessorsMap)                );
    }

    @Override
    public void addNextLinearisedNode(IntGraphNode node) {
        if (linearisedQueue.size() == 0) {
            linearisedQueue.add(node);
        }
        assert linearisedQueue.contains(node) : node + ", " + linearisedQueue;
        linearisedQueue.addFirst(node);
    }

    @Override
    public void addPredecessorPair(IntGraphNode child, IntGraphNode parent) {
        if (!predecessorsMap.containsKey(child)) {
            predecessorsMap.put(child, new HashSet<>());
        }
        predecessorsMap.get(child).add(parent);
    }
}
