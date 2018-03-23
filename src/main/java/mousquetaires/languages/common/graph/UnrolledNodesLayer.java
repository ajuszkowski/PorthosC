package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableSet;

import java.util.Iterator;


public class UnrolledNodesLayer<N extends GraphNode> {
    private final int depth;
    private final ImmutableSet<N> nodes;

    public UnrolledNodesLayer(int depth, ImmutableSet<N> nodes) {
        this.depth = depth;
        this.nodes = nodes;
    }

    public int getDepth() {
        return depth;
    }

    public ImmutableSet<N> getNodes() {
        return nodes;
    }

    public Iterator<N> getNodesIterator() {
        return nodes.iterator();
    }

}
