package mousquetaires.utils.structures;

public interface Graph<T> {
    T entry();

    T exit();

    Iterable<T> children(T node);
}
