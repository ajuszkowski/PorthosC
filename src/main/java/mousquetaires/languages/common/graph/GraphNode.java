package mousquetaires.languages.common.graph;

public interface GraphNode {
    int NON_REFERENCE_ID = -1;
    int FIRST_REFERENCE_ID = 0;
    int LAST_REFERENCE_ID = Integer.MAX_VALUE;

    String getLabel(); // for encoding

    int getReferenceId();
}
