package mousquetaires.languages.common.graph.render;

import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.Renderer;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.Node;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowGraphNode;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Link.to;


public class GraphDumper {

    public static <T extends FlowGraphNode> boolean tryDumpToFile(FlowGraph<T> graph, String filePath, String fileName) {
        Graph vizGraph = graph("graph").directed();

        //HashMap<Integer, Set<String>> levels = new HashMap<>();

        for (boolean b : FlowGraph.edgeKinds()) {
            for (Map.Entry<T, T> pair : graph.getEdges(b).entrySet()) {

                T from = pair.getKey();
                String fromName = from.toString() + "\n{" + from.hashCode() + "}";  // TODO: node serializer
                Node fromNode = node(fromName);
                T to = pair.getValue();
                String toName = to.toString()+ "\n{" + to.hashCode() + "}";  // TODO: node serializer
                Node toNode = node(toName);


                if (from instanceof XEntryEvent) {
                    fromNode = fromNode.with(Shape.INV_TRIANGLE).with(Style.FILLED).with("fillcolor", "gray");
                }
                if (to instanceof XExitEvent) {
                    toNode = toNode.with(Shape.TRIANGLE).with(Style.FILLED).with("fillcolor", "gray");
                }

                //int toRefId = to.getRefId();
                //if (toRefId != FlowGraphNode.NOT_UNROLLED_REF_ID) {
                //    Set<String> set = levels.getOrDefault(toRefId, new HashSet<>());
                //    set.add(toName);
                //    levels.put(toRefId, set);
                //}
                //int fromRefId = from.getRefId();
                //if (fromRefId != FlowGraphNode.NOT_UNROLLED_REF_ID) {
                //    Set<String> set = levels.getOrDefault(fromRefId, new HashSet<>());
                //    set.add(fromName);
                //    levels.put(fromRefId, set);
                //}

                Link edge = to(toNode).with( b ? Style.SOLID : Style.DASHED );

                if (from instanceof XEntryEvent) {
                    edge = edge.attrs().add("weight", 0);
                }

                vizGraph = vizGraph.with(fromNode.link(edge));

                //if (from instanceof XEntryEvent) {
                //    vizGraph = vizGraph.graphAttr().with(Rank.SOURCE).with(fromNode);
                //}
                //if (to instanceof XExitEvent) {
                //    vizGraph = vizGraph.graphAttr().with(Rank.SINK).with(toNode);
                //}
            }
        }
        //vizGraph = vizGraph.graphAttr().with(RankDir.TOP_TO_BOTTOM);
        //for (Map.Entry<Integer, Set<String>> entry : levels.entrySet()) {
        //    StringBuilder sb = new StringBuilder();
        //    for (String node : entry.getValue()) {
        //        sb.append(node).append("; ");
        //    }
        //    vizGraph = vizGraph.graphAttr().with("rank=same; ", sb.toString());
        //}

        Renderer renderer = Graphviz.fromGraph(vizGraph).render(Format.PNG);
        try {
            // TODO: find out why just getParentFile() called for just `new File(filePath + ".png")` returns null
            File file = new File(Paths.get(filePath, fileName + ".png").toFile().getAbsolutePath()); //this is hack for getParentFile() not to return null
            renderer.toFile(file);
            System.out.println("graph dumped to the file " + file.getAbsolutePath());
            return true;
        }
        catch (IOException e) {
            System.err.println("Could not dump graph into file " + StringUtils.wrap(filePath));
            e.printStackTrace();
            return false;
        }
    }
}
