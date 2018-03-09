package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.FlowGraphBuilder;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.*;


public abstract class TestFlowGraphBuilder extends FlowGraphBuilder<TestNode> {
    private final HashMap<TestNode, Integer> referenceMap = new HashMap<>();

    private Set<TestNode> nodes;
    private Map<TestNode, TestNode> edges;
    private Map<TestNode, TestNode> alternativeEdges;
    private Map<TestNode, Set<TestNode>> parents;

    private TestNode source;
    private TestNode sink;

    private boolean isUnrolled = false;

    public TestFlowGraphBuilder() {
        this.nodes = new HashSet<>();
        this.edges = new HashMap<>();
        this.alternativeEdges = new HashMap<>();
        this.parents = new HashMap<>();
    }

    @Override
    public void finish() {
        throw new NotImplementedException(); // leaves, etc...
    }

    @Override
    public void setSource(TestNode source) {
        throw new NotImplementedException("First node added is considered to be the source");
    }

    @Override
    public void setSink(TestNode sink) {
        this.sink = sink;
        for (TestNode node : nodes) {
            if (node.equals(sink)) { continue; }
            if (!edges.containsKey(node)) {
                addEdge(node, sink);
            }
        }
    }

    public abstract void addEdge(int from, int to);

    @Override
    public void addEdge(TestNode from, TestNode to) {
        if (source == null) {
            source = from;
        }

        if (edges.containsKey(from)) {
            throw new IllegalStateException("Already have outgoing edge from node " + from);
        }

        if (source == null) {
            source = from;
        }

        edges.put(from, to);

        if (!parents.containsKey(to)) {
            HashSet<TestNode> newParents = new HashSet<>();
            newParents.add(from);
            parents.put(to, newParents);
        }
        else {
            parents.get(to).add(from);
        }

        nodes.add(from);
        nodes.add(to);
    }


    @Override
    public void addAlternativeEdge(TestNode from, TestNode to) {
        if (alternativeEdges.containsKey(from)) {
            throw new IllegalStateException("Already have outgoing edge from node " + from);
        }
        //if (!edges.containsKey(from)) {
        //    throw new IllegalStateException("Attempt to add alternative edge from " + from + " to " + to +
        //            " before adding the normal edge");
        //}
        alternativeEdges.put(from, to);
    }

    @Override
    public void markAsUnrolled() {
        isUnrolled = true;
    }

    @Override
    public TestFlowGraph build() {
        return new TestFlowGraph(source, sink, nodes, edges, alternativeEdges, isUnrolled);
    }

    public final void addPath(int... nodes) {
        assert nodes.length > 2;
        int from = nodes[0], to;
        for (int i = 1; i < nodes.length; i++) {
            to = nodes[i];
            addEdge(from, to);
            from = to;
        }
    }

    public TestNodeRef nodeToRef(TestNode node) {
        int referenceNumber = 0;
        if (referenceMap.containsKey(node)) {
            referenceNumber = referenceMap.get(node) + 1;
            referenceMap.put(node, referenceNumber);
        }
        return new TestNodeRef(node, referenceNumber);
    }
}
