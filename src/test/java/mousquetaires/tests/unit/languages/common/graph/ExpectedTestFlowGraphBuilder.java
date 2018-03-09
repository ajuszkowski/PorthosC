package mousquetaires.tests.unit.languages.common.graph;

import java.util.HashMap;
import java.util.Map;


public class ExpectedTestFlowGraphBuilder extends TestFlowGraphBuilder {

    private final Map<TestNode, Integer> referenceMap = new HashMap<>();

    @Override
    public void addEdge(int from, int to) {
        super.addEdge(newNodeReference(from), newNodeReference(to));
    }

    public void addAlternativeEdge(int from, int to) {
        super.addAlternativeEdge(newNodeReference(from), newNodeReference(to));
    }

    public void finishBuilding(int sink) {
        super.setSink(newNodeReference(sink));
    }

    private TestNodeRef newNodeReference(int value) {
        int refId = 0;
        TestNode node = new TestNode(value);
        if (referenceMap.containsKey(node)) {
            refId = referenceMap.get(node) + 1;
        }
        referenceMap.put(node, refId);
        return new TestNodeRef(node, refId);
    }
}