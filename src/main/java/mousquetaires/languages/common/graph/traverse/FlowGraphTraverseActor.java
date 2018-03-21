package mousquetaires.languages.common.graph.traverse;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.GraphNode;


interface FlowGraphTraverseActor<N extends GraphNode, G extends FlowGraph<N>> {

    default void onStart() {
        // do nothing yet
    }

    default void onNodePreVisit(N node) {
        // do nothing yet
    }

    default void onEdgeVisit(boolean edgeKind, N from, N to) {
        // do nothing yet
    }

    default void onNodePostVisit(N node) {
        // do nothing yet
    }

    default void onBoundAchieved(N lastNode) {
        // do nothing yet
    }

    default void onFinish() {
        // do nothing yet
    }
}
