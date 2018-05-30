package mousquetaires.languages.syntax.xgraph.process;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.syntax.xgraph.events.XEvent;


public class XProcessHelper {

    public static <N extends XEvent, G extends FlowGraph<N>, S extends N>
    int getNodesCount(G g, Class<S> type) {
        int count = 0;
        for (N event : g.getAllNodesExceptSource()) {
            if (type.isAssignableFrom(event.getClass())) {
                count++;
            }
        }
        return count;
    }
}
