package mousquetaires.languages.common.graph;

import java.util.Map;
import java.util.Set;


public interface FlowGraph<T> {

    T source();
    T sink();

    T child(T node);
    T alternativeChild(T node);
    boolean hasAlternativeChild(T node);

    Set<T> parents(T node);

    Set<T> nodes();
    Map<T, T> edges();
    Map<T, T> alternativeEdges();
}
