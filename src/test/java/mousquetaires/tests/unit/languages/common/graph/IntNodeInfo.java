package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.FlowGraphNodeInfo;


public class IntNodeInfo extends FlowGraphNodeInfo {

    IntNodeInfo() {
    }

    IntNodeInfo(int unrollingDepth) {
        super(unrollingDepth);
    }

    @Override
    public IntNodeInfo withUnrollingDepth(int newDepth) {
        return new IntNodeInfo(newDepth);
    }

    @Override
    public String toString() {
        return "d=" + getUnrollingDepth();
    }
}
