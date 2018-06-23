package porthosc.app.modules.dartagnan;

import porthosc.app.modules.AppVerdict;
import porthosc.app.options.AppOptions;
import porthosc.languages.common.graph.FlowGraph;
import porthosc.languages.syntax.xgraph.events.XEvent;
import porthosc.languages.syntax.xgraph.process.XProcessId;

import static porthosc.languages.syntax.xgraph.process.XProcessHelper.getNodesCount;


public class DartagnanVerdict extends AppVerdict {
    public enum Status {
        Reachable,
        NonReachable,
    }

    public Status result;

    public DartagnanVerdict(AppOptions options) {
        super(options);
    }

}
