package mousquetaires.languages.common.graph;

import mousquetaires.utils.patterns.Builder;


public abstract class FlowGraphBuilder<T> extends Builder<FlowGraph<T>> {

    public abstract void setSource(T source);

    public abstract void setSink(T sink);

    public abstract void addEdge(T from, T to);

    public abstract void addAlternativeEdge(T from, T to);

    public abstract void markAsUnrolled();

    public abstract void finish();
}