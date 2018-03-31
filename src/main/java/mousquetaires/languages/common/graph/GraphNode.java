package mousquetaires.languages.common.graph;

public interface GraphNode {
    int NON_REFERENCE_ID = -1;
    int FIRST_NODE_REFERENCE_ID = 0;
    int LAST_NODE_REFERENCE_ID = Integer.MAX_VALUE;

    String nodeId(); // for encoding

    int getReferenceId();

    default boolean isReference() {
        return getReferenceId() != NON_REFERENCE_ID;
    }
}
