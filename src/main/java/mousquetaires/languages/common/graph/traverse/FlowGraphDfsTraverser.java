package mousquetaires.languages.common.graph.traverse;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.GraphNode;
import mousquetaires.languages.common.graph.UnrolledFlowGraph;
import mousquetaires.languages.common.graph.UnrolledFlowGraphBuilder;

import java.util.*;
import java.util.function.BiFunction;


public abstract class FlowGraphDfsTraverser<N extends GraphNode, G extends UnrolledFlowGraph<N>> {

    private final int unrollingBound;
    private final FlowGraph<N> graph;
    private final BiFunction<N, Integer, N> createNodeRef;

    private final CompositeTraverseActor<N, G> actor;

    private Stack<N> depthStack;
    private HashMap<N, Set<N>> backEdges;

    protected FlowGraphDfsTraverser(FlowGraph<N> graph,
                                    UnrolledFlowGraphBuilder<N, G> builder,
                                    BiFunction<N, Integer, N> createNodeRef,
                                    int unrollingBound) {
        this.graph = graph;
        this.createNodeRef = createNodeRef;
        this.unrollingBound = unrollingBound;
        this.actor = new CompositeTraverseActor<>(builder);
        this.depthStack = new Stack<>();
        this.backEdges = new HashMap<>();
    }

    public G getProcessedGraph() {
        return actor.buildGraph();
    }

    public void doUnroll() {
        actor.onStart();
        unrollRecursively(graph.source(), graph.source(), 0, true);
        actor.onFinish();
    }

    private void unrollRecursively(N node, N nodeRef, int depth, boolean needToUnrollChildren) {
        depthStack.push(node);

        actor.onNodePreVisit(nodeRef);

        if (needToUnrollChildren) {
            unrollChildRecursively(true, node, nodeRef, depth + 1);
            unrollChildRecursively(false, node, nodeRef, depth + 1);
        }

        N popped = depthStack.pop();
        assert popped == node : popped + " must be " + node;
        actor.onNodePostVisit(nodeRef);
    }

    private void unrollChildRecursively(boolean edgeSign, N parent, N parentRef, int childDepth) {
        if (!graph.hasChild(edgeSign, parent)) { return; }

        N child = graph.successor(edgeSign, parent);

        boolean isBackEdge = isMemoisedBackEdge(parent, child);
        if (!isBackEdge && depthStack.contains(child)) {
            memoiseBackEdge(parent, child);
            isBackEdge = true;
        }

        boolean isSink = (child == graph.sink());
        boolean boundAchieved = (childDepth > unrollingBound);
        boolean needToUnrollGrandChildren = !(isSink || boundAchieved);

        N childRef = needToUnrollGrandChildren
                ? createNodeRef.apply(child, childDepth)
                : getSinkNode(isSink || isBackEdge);

        actor.onEdgeVisit(edgeSign, parentRef, childRef);

        if (boundAchieved) {
            actor.onBoundAchieved(parentRef);
        }
        unrollRecursively(child, childRef, childDepth, needToUnrollGrandChildren);
    }

    private N getSinkNode(boolean completed) {
        return completed
                ? graph.sink()
                : graph.sink(); // TODO: create incompleted sink node here
    }

    private boolean isMemoisedBackEdge(N from, N to) {
        return backEdges.containsKey(from) && backEdges.get(from).contains(to);
    }

    private void memoiseBackEdge(N from, N to) {
        if (!backEdges.containsKey(from)) {
            backEdges.put(from, new HashSet<>());
        }
        backEdges.get(from).add(to);
    }
}
