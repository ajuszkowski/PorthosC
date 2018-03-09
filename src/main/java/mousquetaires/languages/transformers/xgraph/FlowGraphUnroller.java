package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowGraphBuilder;


public abstract class FlowGraphUnroller<T> {

    private final FlowGraph<T> graph;

    public FlowGraphUnroller(FlowGraph<T> cyclicGraph) {
        this.graph = cyclicGraph;
    }

    //protected abstract FlowGraphBuilder<T> createBuilder();

    protected abstract T createNodeRef(T node, int refId);

    public void unroll(int bound, FlowGraphBuilder<T> builder) {
        builder.setSource(graph.source());
        builder.setSink(graph.sink());
        buildUnrolledGraphRecursively(graph.source(), 0, bound, builder);
        builder.markAsUnrolled();
        builder.finish();
        //return builder;
    }

    private void buildUnrolledGraphRecursively(T node, int counter, int bound, FlowGraphBuilder<T> builder) {
        if (counter == bound - 1) { return; }

        if (node.equals(graph.sink())) {
            return;
        }

        T nodeRef = node.equals(graph.source())
                ? graph.source()
                : createNodeRef(node, counter);

        int childrenCounter = counter + 1;

        // epsilon edge or true-edge
        T child = node.equals(graph.sink())
                ? graph.sink()
                : graph.child(node);
        T childRef = createNodeRef(child, childrenCounter);
        builder.addEdge(nodeRef, childRef);
        buildUnrolledGraphRecursively(child, childrenCounter, bound, builder);

        // false-edge
        if (graph.hasAlternativeChild(node)) {
            T altChild = graph.alternativeChild(node);
            T altChildRef = createNodeRef(altChild, childrenCounter);
            builder.addAlternativeEdge(nodeRef, altChildRef);
            buildUnrolledGraphRecursively(altChild, childrenCounter, bound, builder);
        }
    }
}
