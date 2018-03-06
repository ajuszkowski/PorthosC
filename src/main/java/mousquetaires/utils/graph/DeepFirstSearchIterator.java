package mousquetaires.utils.graph;

import mousquetaires.utils.CollectionUtils;
import mousquetaires.utils.structures.Graph;

import java.util.*;


public abstract class DeepFirstSearchIterator<T> implements Iterator<T> {

    private final Graph<T> graph;
    private final Stack<Iterator<T>> layersStack = new Stack<>();
    private final Stack<T> entered = new Stack<>();
    private final Set<T> finished = new HashSet<>();

    public DeepFirstSearchIterator(Graph<T> graph) {
        this.graph = graph;
        layersStack.push(CollectionUtils.createIteratorFrom(graph.entry()));
    }

    protected abstract void preVisit(T node);

    protected abstract void postVisit(T node);


    @Override
    public boolean hasNext() {
        while (!layersStack.isEmpty()) {
            Iterator<T> layer = layersStack.peek();
            if (layer.hasNext()) {
                entered.push(layer.next());
                return true;
            }
            else {
                T lastEntered = entered.pop();
                postVisit(lastEntered);
                layersStack.pop();
            }
        }
        return false;
    }

    @Override
    public T next() {
        assert !entered.isEmpty();
        T node = entered.peek();
        preVisit(node);

        LinkedList<T> nextLayer = new LinkedList<>();
        for (T child : graph.children(node)) {
            if (!visited(child)) {
                nextLayer.add(child);
            }
        }
        layersStack.push(nextLayer.iterator());

        return node;
    }

    private boolean visited(T node) {
        return entered.contains(node) || finished.contains(node);
    }

}

