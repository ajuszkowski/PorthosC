package mousquetaires.languages.common.graph.traverse;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.GraphNode;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.function.BiFunction;


public abstract class FlowGraphDfsTraverser<N extends GraphNode, G extends UnrolledFlowGraph<N>> {

    private final int unrollingBound;
    private final FlowGraph<N> graph;
    private final BiFunction<N, Integer, N> createNodeRef;

    private final CompositeTraverseActor<N, G> actor;

    private HashMap<N, Integer> nodeCounterMap;
    private Stack<N> enteredNodesStack;
    private Set<N> finishedNodesRefs;

    protected FlowGraphDfsTraverser(FlowGraph<N> graph,
                                    UnrolledFlowGraphBuilder<N, G> builder,
                                    BiFunction<N, Integer, N> createNodeRef,
                                    int unrollingBound) {
        this.graph = graph;
        this.createNodeRef = createNodeRef;
        this.unrollingBound = unrollingBound;
        this.actor = new CompositeTraverseActor<>(builder);
        this.nodeCounterMap = new HashMap<>();
        this.enteredNodesStack = new Stack<>();
        this.finishedNodesRefs = new HashSet<>();
    }

    public G getProcessedGraph() {
        return actor.buildGraph();
    }

    public void doUnroll() {
        actor.onStart();
        unrollRecursively(graph.source(), 0, true);
        actor.onFinish();
    }

    private void unrollRecursively(N node, int counter, boolean unrollChildren) {
        N nodeRef = getParentRef(node);
        enteredNodesStack.push(node);
        actor.onNodePreVisit(nodeRef);

        if (unrollChildren) {
            int childCounter = counter + 1;

            if (graph.hasChild(true, node)) {
                unrollChildRecursively(node, nodeRef, childCounter, true);
            }
            if (graph.hasChild(false, node)) {
                unrollChildRecursively(node, nodeRef, childCounter, false);
            }
        }

        N popped = enteredNodesStack.pop();
        assert popped == node : popped + " must be " + node;
        finishedNodesRefs.add(nodeRef);
        actor.onNodePostVisit(nodeRef);
    }

    private void unrollChildRecursively(N node, N nodeRef, int childrensCounter, boolean edgeSign) {
        N child = graph.successor(edgeSign, node);
        boolean isSink = (child == graph.sink());
        boolean needToUnrollGrandChildren = !isSink;
        boolean boundAchieved = (childrensCounter > unrollingBound);

        N childRef = isSink || boundAchieved
                ? graph.sink()
                : getOrCreateChildRef(child);

        actor.onEdgeVisit(edgeSign, nodeRef, childRef);

        boolean alreadyVisited = finishedNodesRefs.contains(childRef);
        if (boundAchieved) {
            actor.onBoundAchieved(nodeRef);
        }
        else if (!alreadyVisited) {
            unrollRecursively(child, childrensCounter, needToUnrollGrandChildren);
        }
    }

    private N getParentRef(N node) {
        return getNodeRef(node, getLastIndex(node));
    }

    private N getOrCreateChildRef(N node) {
        int index = isBackEdgeDestination(node)
                ? getNewIndex(node)
                : getLastIndex(node);
        return getNodeRef(node, index);
    }

    private int getNewIndex(N node) {
        int index = getLastIndex(node) + 1;
        nodeCounterMap.put(node, index);
        return index;
    }

    private int getLastIndex(N node) {
        if (!nodeCounterMap.containsKey(node)) {
            nodeCounterMap.put(node, 1);
        }
        return nodeCounterMap.get(node);
    }

    private boolean isBackEdgeDestination(N node) {
        return enteredNodesStack.contains(node);
    }

    private N getNodeRef(N node, int index) {
        if (node == graph.source() || node == graph.sink()) {
            return node;
        }
        return createNodeRef.apply(node, index);
    }
}
