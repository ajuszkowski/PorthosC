package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.InformativeFlowGraph;

import java.util.Map;
import java.util.Set;


public class TestFlowGraph implements InformativeFlowGraph<TestNode> {

    private TestNode source;
    private TestNode sink;

    //private List<TestNode> nodesLinearised;
    private Map<TestNode, TestNode> edges;
    private Map<TestNode, TestNode> alternativeEdges;
    private Map<TestNode, Set<TestNode>> parents;
    private boolean isUnrolled;
    private final int nodesCount;

    public TestFlowGraph(TestNode source,
                         TestNode sink,
                         Map<TestNode, TestNode> edges,
                         Map<TestNode, TestNode> alternativeEdges,
                         //List<TestNode> nodesLinearised,
                         boolean isUnrolled) {
        this.source = source;
        this.sink = sink;
        this.edges = edges;
        this.alternativeEdges = alternativeEdges;
        //this.nodesLinearised = nodesLinearised;
        this.isUnrolled = isUnrolled;
        this.nodesCount = edges.keySet().size();
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
    public boolean isAcyclic() {
        return isUnrolled;
    }

    @Override
    public int nodesCount() {
        return nodesCount;
    }

    @Override
    public Map<TestNode, TestNode> edges() {
        return edges;
    }

    @Override
    public Map<TestNode, TestNode> alternativeEdges() {
        return alternativeEdges;
    }

    //@Override
    //public Iterable<TestNode> linearisedNodes() {
    //    return nodesLinearised;
    //}
}
