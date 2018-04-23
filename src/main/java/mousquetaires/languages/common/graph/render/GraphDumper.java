package mousquetaires.languages.common.graph.render;

import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.Renderer;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.Node;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowGraphNode;
import mousquetaires.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Link.to;


public class GraphDumper {

    public static <T extends FlowGraphNode> boolean tryDumpToFile(FlowGraph<T> graph, String filePath, String fileName) {
        Graph vizGraph = graph("graph").directed();
        for (boolean b : FlowGraph.edgeKinds()) {
            for (Map.Entry<T, T> pair : graph.getEdges(b).entrySet()) {

                T from = pair.getKey();
                Node fromNode = node(from.toString() + "\n{" + from.hashCode() + "}");  // TODO: node serializer

                T to = pair.getValue();
                Node toNode = node(to.toString() + "\n{" + to.hashCode() + "}");  // TODO: node serializer

                Link edge = to(toNode).with( b ? Style.SOLID : Style.DASHED );
                vizGraph = vizGraph.with(fromNode.link(edge));
            }
        }
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
