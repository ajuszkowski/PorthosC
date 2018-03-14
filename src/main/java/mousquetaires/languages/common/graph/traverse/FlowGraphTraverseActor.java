package mousquetaires.languages.common.graph.traverse;

import mousquetaires.languages.common.graph.GraphNode;


public interface FlowGraphTraverseActor<T extends GraphNode> {

    void onStart();

    void onVisitEdge(T from, T to);

    void onVisitAltEdge(T from, T to);

    void onBoundAchieved(T lastNode);

    void onFinish();
}
