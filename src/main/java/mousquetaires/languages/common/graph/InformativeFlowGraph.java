package mousquetaires.languages.common.graph;

import java.util.Map;


public interface InformativeFlowGraph<T> extends FlowGraph<T> {

    Iterable<T> parents(T node);

    int nodesCount();

    //int edgesCount();

    //Iterable<T> linearisedNodes();

    Map<T, T> edges();

    Map<T, T> alternativeEdges();
}
