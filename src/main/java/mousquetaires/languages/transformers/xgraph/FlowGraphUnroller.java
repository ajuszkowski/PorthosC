package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowGraphBuilder;

import java.util.*;


public abstract class FlowGraphUnroller<T> {

    private final FlowGraph<T> graph;
    private Map<T, List<T>> referenceMap;

    public FlowGraphUnroller(FlowGraph<T> cyclicGraph) {
        this.graph = cyclicGraph;
    }

    protected abstract FlowGraphBuilder<T> createBuilder();

    protected abstract T createNodeRef(T node, int refId);

    public FlowGraph<T> unroll(int bound) {
        referenceMap = new HashMap<>(); //todo: initial capacity with respect to unroll bound
        FlowGraphBuilder<T> builder = createBuilder();
        buildUnrolledGraphRecursively(graph.source(), 0, bound, builder);
        return builder.build();
    }

    private void buildUnrolledGraphRecursively(T node, int counter, int bound, FlowGraphBuilder<T> builder) {
        if (counter == bound - 1) { return; }

        T nodeRef = createNodeRef(node, counter);
        registerNodeRef(nodeRef, node);

        int childrenCounter = counter + 1;

        // epsilon edge or true-edge
        T child = graph.child(node);
        T childRef = createNodeRef(child, childrenCounter);
        registerNodeRef(childRef, child);
        builder.addEdge(nodeRef, childRef);
        buildUnrolledGraphRecursively(child, childrenCounter, bound, builder);

        // false-edge
        if (graph.hasAlternativeChild(node)) {
            T altChild = graph.alternativeChild(node);
            T altChildRef = createNodeRef(altChild, childrenCounter);
            registerNodeRef(altChildRef, altChild);
            builder.addAlternativeEdge(nodeRef, altChildRef);
            buildUnrolledGraphRecursively(altChild, childrenCounter, bound, builder);
        }
    }

    private void registerNodeRef(T nodeRef, T node) {
        if (!referenceMap.containsKey(node)) {
            LinkedList<T> newReferences = new LinkedList<>();
            newReferences.add(nodeRef);
            referenceMap.put(node, newReferences);
        }
        else {
            referenceMap.get(node).add(nodeRef);
        }
    }
}
