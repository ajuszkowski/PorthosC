package mousquetaires.languages.common.graph;

// TODO: generalise these interfaces into abstract classes (+ builders) !
public interface FlowGraph<T> {
    T source();

    T sink();

    T child(T node);

    T alternativeChild(T node);

    boolean hasAlternativeChild(T node);

    default boolean hasChild(T node) {
        return !node.equals(sink());
    }

    boolean isAcyclic();
}
