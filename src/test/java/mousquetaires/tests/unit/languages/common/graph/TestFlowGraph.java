package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.FlowGraph;

import java.util.Map;
import java.util.Set;


public class TestFlowGraph implements FlowGraph<TestNode>  {

    private TestNode source;
    private TestNode sink;

    private Set<TestNode> nodes;
    private Map<TestNode, TestNode> edges;
    private Map<TestNode, TestNode> alternativeEdges;
    private Map<TestNode, Set<TestNode>> parents;
    private final boolean isUnrolled;

    public TestFlowGraph(TestNode source,
                         TestNode sink,
                         Set<TestNode> nodes,
                         Map<TestNode, TestNode> edges,
                         Map<TestNode, TestNode> alternativeEdges,
                         boolean isUnrolled) {
        this.source = source;
        this.sink = sink;
        this.nodes = nodes;
        this.edges = edges;
        this.alternativeEdges = alternativeEdges;
        this.isUnrolled = isUnrolled;;
    }

    @Override
    public TestNode source() {
        return source;
    }

    @Override
    public TestNode sink() {
        return sink;
    }

    @Override
    public TestNode child(TestNode node) {
        if (!edges.containsKey(node)) {
            throw new IllegalArgumentException("Cannot find graph node " + node);
        }
        return edges.get(node);
    }

    @Override
    public boolean hasAlternativeChild(TestNode node) {
        return alternativeEdges.containsKey(node);
    }

    @Override
    public TestNode alternativeChild(TestNode node) {
        return alternativeEdges.get(node);
    }

    @Override
    public Set<TestNode> parents(TestNode node) {
        return parents.containsKey(node)
                ? parents.get(node)
                : Set.of();
    }

    @Override
    public Set<TestNode> nodes() {
        return nodes;
    }

    @Override
    public Map<TestNode, TestNode> edges() {
        return edges;
    }

    @Override
    public Map<TestNode, TestNode> alternativeEdges() {
        return alternativeEdges;
    }
}
