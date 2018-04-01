package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.syntax.xgraph.events.XEvent;


public abstract class XProgramBase<G extends FlowGraph<XEvent>> {

    //private final XPreProcess prelude;
    private final ImmutableList<G> processes;
    //private final XPostProcess postlude;

    XProgramBase(ImmutableList<G> processes) {
        //this.prelude = prelude;
        this.processes = processes;
        //this.postlude = postlude;
    }

    public ImmutableList<G> getAllProcesses() {
        return processes;
    }

    public int size() {
        int result = 0;
        for (G process : processes) {
            result += process.size();
        }
        return result;
    }
}