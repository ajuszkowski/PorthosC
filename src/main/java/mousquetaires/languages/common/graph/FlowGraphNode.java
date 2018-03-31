package mousquetaires.languages.common.graph;

public interface FlowGraphNode<T extends FlowGraphNodeInfo> {

    T getInfo();

    FlowGraphNode withInfo(T newInfo);
}
