//package mousquetaires.utils.graphs;
//
//import com.google.common.collect.ImmutableList;
//import mousquetaires.languages.common.graph.FlowGraph;
//
//import java.util.ArrayDeque;
//import java.util.Deque;
//import java.util.HashSet;
//import java.util.Set;
//
//
//// todo: better: graph information collector
//public class GraphLineariser<T> {
//
//    private final FlowGraph<T> graph;
//
//    private ImmutableList<T> result;
//    private Deque<T> resultQueue;
//    private Set<T> visited;
//
//    public GraphLineariser(InformativeFlowGraph<T> graph) {
//        assert graph.isAcyclic() : "input graph is considered to be acyclic";
//        this.graph = graph;
//    }
//
//    public ImmutableList<T> computeLinearisedNodes() {
//        if (result == null) {
//            resultQueue = new ArrayDeque<>(graph.size());
//            visited = new HashSet<>();
//            visit(graph.source());
//            result = ImmutableList.copyOf(resultQueue);
//        }
//        return result;
//    }
//
//    private void visit(T node) {
//        if (!visited.add(node)) {
//            return;
//        }
//        if (!node.equals(graph.sink())) {
//            visit(graph.successor(node));
//            if (graph.hasAlternativeChild(node)) {
//                visit(graph.alternativeChild(node));
//            }
//        }
//        resultQueue.addLast(node);
//    }
//}
