package mousquetaires.languages.common.graph;

import mousquetaires.languages.common.NamedAtom;


public interface FlowGraphNode<T extends FlowGraphNodeInfo> extends NamedAtom {

    T getInfo();

    FlowGraphNode withInfo(T newInfo);
}
