package mousquetaires.tests.unit.languages.common.graph;

import mousquetaires.languages.common.graph.FlowGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class TestFlowGraph implements FlowGraph<Integer>  {

    private Set<Integer> nodes;
    private Map<Integer, Integer> chidren;
    private Map<Integer, Integer> chidrenAlternative;
    private Map<Integer, Set<Integer>> parents;

    private Integer source;
    private Integer sink;

    public TestFlowGraph() {
        this.nodes = new HashSet<>();
        this.chidren = new HashMap<>();
        this.chidrenAlternative = new HashMap<>();
        this.parents = new HashMap<>();
    }

    @Override
    public Integer source() {
        return source;
    }

    @Override
    public Integer sink() {
        return sink;
    }

    @Override
    public Integer child(Integer node) {
        if (!chidren.containsKey(node)) {
            throw new IllegalArgumentException("Cannot find graph node " + node);
        }
        return chidren.get(node);
    }

    @Override
    public boolean hasAlternativeChild(Integer node) {
        return chidrenAlternative.containsKey(node);
    }

    @Override
    public Integer alternativeChild(Integer node) {
        return chidrenAlternative.get(node);
    }

    @Override
    public Set<Integer> parents(Integer node) {
        return parents.containsKey(node)
                ? parents.get(node)
                : Set.of();
    }

    @Override
    public Set<Integer> allNodes() {
        return nodes;
    }
}
