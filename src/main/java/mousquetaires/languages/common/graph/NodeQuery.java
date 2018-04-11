package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableSet;

import java.util.function.Predicate;


public class NodeQuery<N extends FlowGraphNode> {
    private final Predicate<N> filter;
    private final ImmutableSet<? extends N> result;

    public NodeQuery(Predicate<N> filter, ImmutableSet<? extends N> result) {
        this.filter = filter;
        this.result = result;
    }
}
