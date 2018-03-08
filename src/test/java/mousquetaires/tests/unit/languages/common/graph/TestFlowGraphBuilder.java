package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowGraphBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class TestFlowGraphBuilder extends FlowGraphBuilder<Integer> {

    private Set<Integer> nodes;
    private Map<Integer, Integer> chidren;
    private Map<Integer, Integer> chidrenAlternative;
    private Map<Integer, Set<Integer>> parents;

    private Integer source;
    private Integer sink;

    public TestFlowGraphBuilder() {
        this.nodes = new HashSet<>();
        this.chidren = new HashMap<>();
        this.chidrenAlternative = new HashMap<>();
        this.parents = new HashMap<>();
    }

    @Override
    public void setSource(Integer source) {
        this.source = source;
    }

    @Override
    public void setSink(Integer sink) {
        this.sink = sink;
    }

    @Override
    public void addEdge(Integer from, Integer to) {
        if (chidren.containsKey(from)) {
            throw new IllegalStateException("Already have outgoing edge from node " + from);
        }

        if (source == null) {
            source = from;
        }

        chidren.put(from, to);

        if (!parents.containsKey(to)) {
            HashSet<Integer> newParents = new HashSet<>();
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
    public void addAlternativeEdge(Integer from, Integer to) {
        if (chidrenAlternative.containsKey(from)) {
            throw new IllegalStateException("Already have outgoing edge from node " + from);
        }
        if (!chidren.containsKey(from)) {
            throw new IllegalStateException("Attempt to add alternative edge from " + from + " to " + to +
                    " before adding the normal edge");
        }
        chidrenAlternative.put(from, to);
    }

    @Override
    public FlowGraph<Integer> build() {
        return null;
    }

    public void addPath(int... nodes) {
        assert nodes.length > 2;
        int from = nodes[0], to;
        for (int i = 1; i < nodes.length; i++) {
            to = nodes[i];
            addEdge(from, to);
            from = to;
        }
    }
}
