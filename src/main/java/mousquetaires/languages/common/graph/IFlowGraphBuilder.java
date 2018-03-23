package mousquetaires.languages.common.graph;

import com.google.common.collect.ImmutableMap;
import mousquetaires.utils.patterns.Builder;


public interface IFlowGraphBuilder<N extends GraphNode, G extends FlowGraph<N>> extends Builder<G> {

    N getSource();

    N getSink();

    ImmutableMap<N, N> buildEdges(boolean edgeSign);

    void finishBuilding();

    void setSource(N source);

    void setSink(N sink);

    void addEdge(boolean edgeSign, N from, N to);

    boolean hasChildren(N node);

    boolean hasEdge(boolean edgeSign, N from, N to);
}
