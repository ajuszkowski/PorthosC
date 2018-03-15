package mousquetaires.languages.common.graph.traverse;

import mousquetaires.languages.common.graph.Node;


public interface FlowGraphTraverseActor<T extends Node> {

    void onStart();

    void onVisitEdge(T from, T to);

    void onVisitAltEdge(T from, T to);

    void onBoundAchieved(T lastNode, T sinkNode);

    void onFinish();
}
