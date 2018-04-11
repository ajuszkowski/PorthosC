package mousquetaires.languages.common.graph;

import mousquetaires.languages.common.NamedAtom;


public interface FlowGraphNode extends NamedAtom {

    int NOT_UNROLLED_REF_ID = -1;
    int SOURCE_NODE_REF_ID = 0;
    int SINK_NODE_REF_ID = Integer.MAX_VALUE;

    int getId();

    int getRefId();
}
