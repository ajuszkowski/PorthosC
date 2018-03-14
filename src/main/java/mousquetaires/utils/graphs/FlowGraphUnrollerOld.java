//package mousquetaires.utils.graphs;
//
//import mousquetaires.languages.common.graph.FlowGraph;
//import mousquetaires.languages.common.graph.FlowGraphBuilder;
//
//import java.util.HashMap;
//import java.util.Stack;
//
//
//public abstract class \<T> {
//
//    private final FlowGraph<T> graph;
//    private Stack<T> enteredStack;
//    private HashMap<T, Integer> lastReferences;
//    private final int bound;
//
//    // todo: maybe pass the lambda function that creates the graph builder ?
//    public FlowGraphUnroller(FlowGraph<T> cyclicGraph, int bound) {
//        this.graph = cyclicGraph;
//        this.bound = bound;
//    }
//
//    //protected abstract FlowGraphBuilder<T> createBuilder();
//
//    protected abstract T createNodeRef(T node, int refId);
//
//    public void unrollIntoBuilder(FlowGraphBuilder<T> builder) {
//        enteredStack = new Stack<>();
//        lastReferences = new HashMap<>();
//        builder.setSource(graph.source());
//        builder.setSink(graph.sink());
//        buildUnrolledGraphRecursively(graph.source(), 0, builder);
//        builder.markAsUnrolled();
//        builder.finishBuilding();
//        //return builder;
//    }
//
//    private void buildUnrolledGraphRecursively(T node, int counter, FlowGraphBuilder<T> builder) {
//        if (node.equals(graph.sink())) { return; }
//
//        T nodeRef = node.equals(graph.source())
//                ? graph.source()
//                : getOrCreateNodeRef(node, counter);
//
//        // TODO: Probably algorithm breaks if the DFS firstly discovers node with not largest path
//
//        if (counter == bound - 1) {
//            builder.addEdge(nodeRef, graph.sink());
//            return;
//        }
//
//        enteredStack.push(node);
//        int counterForChildren = counter + 1;
//
//        // epsilon edge or true-edge
//        T child = graph.child(node);
//        if (child.equals(graph.sink())) {
//            builder.addEdge(nodeRef, child);
//        }
//        else {
//            T childRef = enteredStack.contains(child)
//                    ? createNodeRef     (child, counterForChildren)
//                    : getOrCreateNodeRef(child, counterForChildren);
//
//            builder.addEdge(nodeRef, childRef);
//            buildUnrolledGraphRecursively(child, counterForChildren, builder);
//        }
//
//        // false-edge
//        if (graph.hasAlternativeChild(node)) {
//            T altChild = graph.alternativeChild(node);
//            if (altChild.equals(graph.sink())) {
//                builder.addAlternativeEdge(nodeRef, altChild);
//            }
//            else {
//                T altChildRef = enteredStack.contains(altChild)
//                        ? createNodeRef     (altChild, counterForChildren)
//                        : getOrCreateNodeRef(altChild, counterForChildren);
//
//                builder.addAlternativeEdge(nodeRef, altChildRef);
//                buildUnrolledGraphRecursively(altChild, counterForChildren, builder);
//            }
//        }
//
//        T popped =  enteredStack.pop();
//        assert popped == node : popped + ", " + node;
//    }
//
//    private T getOrCreateNodeRef(T node, int childCounter) {
//        int childIndex = getOrCreateIndex(node, childCounter);
//        return createNodeRef(node, childIndex);
//    }
//
//    private int getOrCreateIndex(T node, int defaultValue) {
//        if (!enteredStack.contains(node) && lastReferences.containsKey(node)) {
//            return lastReferences.get(node);
//        }
//        lastReferences.put(node, defaultValue);
//        return defaultValue;
//    }
//}
