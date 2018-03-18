package mousquetaires.languages.common.graph.traverse;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.GraphNode;

import java.util.*;
import java.util.function.BiFunction;


public abstract class FlowGraphUnrollingTraverser<T extends GraphNode, G extends FlowGraph<T>> {

    private final int unrollingBound;
    private final FlowGraph<T> graph;
    private final BiFunction<T, Integer, T> createNodeRef;

    private final FlowGraphUnrollingActor<T, G> unrollingActor;
    private final CompositeFlowGraphTraverseActor<T> actors;

    private HashMap<T, Integer> nodeCounterMap;
    private Stack<T> enteredNodesStack;
    private Set<T> finishedNodesRefs;

    // TODO: check this heap pollution!
    protected FlowGraphUnrollingTraverser(FlowGraph<T> graph,
                                          BiFunction<T, Integer, T> createNodeRef,
                                          int unrollingBound,
                                          FlowGraphUnrollingActor<T, G> unrollingActor,
                                          FlowGraphTraverseActor<T>... actors) {
        this.graph = graph;
        this.createNodeRef = createNodeRef;
        this.unrollingBound = unrollingBound;

        this.unrollingActor = unrollingActor;
        HashSet<FlowGraphTraverseActor<T>> allActors = new HashSet<>();
        allActors.add(unrollingActor);
        allActors.addAll(Arrays.asList(actors));
        this.actors = new CompositeFlowGraphTraverseActor<>(allActors);

        this.nodeCounterMap = new HashMap<>();
        this.enteredNodesStack = new Stack<>();
        this.finishedNodesRefs = new HashSet<>();
    }

    public ImmutableSet<FlowGraphTraverseActor<T>> getActors() {
        return actors.getActors();
    }

    public G getUnrolledGraph() {
        return unrollingActor.build();
    }

    public void doUnroll() {
        actors.onStart();
        unrollRecursively(graph.source(), 0);
        actors.onFinish();
    }

    private void unrollRecursively(T node, int counter) {
        if (node.equals(graph.sink())) { return; }

        T nodeRef = getParentRef(node);
        enteredNodesStack.push(node);

        int childCounter = counter + 1;

        if (graph.hasChild(true, node)) {
            unrollChildRecursively(node, nodeRef, childCounter, true);
        }
        if (graph.hasChild(false, node)) {
            unrollChildRecursively(node, nodeRef, childCounter, false);
        }

        T popped =  enteredNodesStack.pop();
        assert popped == node : popped + " must be " + node;
        finishedNodesRefs.add(nodeRef);
    }

    private void unrollChildRecursively(T node, T nodeRef, int childrensCounter, boolean edgeSign) {
        T child = graph.successor(edgeSign, node);
        boolean childIsSink = (child == graph.sink());
        boolean boundAchieved = (childrensCounter > unrollingBound);
        boolean finished = childIsSink || boundAchieved;

        T childRef = finished
                ? graph.sink()
                : getOrCreateChildRef(child);

        actors.onVisitEdge(edgeSign, nodeRef, childRef);

        if (finished) {
            actors.onBoundAchieved(nodeRef);
        }
        else if (!finishedNodesRefs.contains(childRef)) {
            unrollRecursively(child, childrensCounter);
        }
    }

    private T getParentRef(T node) {
        return getNodeRef(node, getLastIndex(node));
    }

    private T getOrCreateChildRef(T node) {
        int index = isBackEdgeDestination(node)
                ? getNewIndex(node)
                : getLastIndex(node);
        return getNodeRef(node, index);
    }

    private int getNewIndex(T node) {
        int index = getLastIndex(node) + 1;
        nodeCounterMap.put(node, index);
        return index;
    }

    private int getLastIndex(T node) {
        if (!nodeCounterMap.containsKey(node)) {
            nodeCounterMap.put(node, 1);
        }
        return nodeCounterMap.get(node);
    }

    private boolean isBackEdgeDestination(T node) {
        return enteredNodesStack.contains(node);
    }

    private T getNodeRef(T node, int index) {
        if (node == graph.source() || node == graph.sink()) {
            return node;
        }
        return createNodeRef.apply(node, index);
    }
}
