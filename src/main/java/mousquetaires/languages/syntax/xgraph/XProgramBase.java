package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.FlowTree;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XSharedMemoryEvent;


public abstract class XProgramBase<P extends FlowGraph<XEvent>>
        extends FlowTree<XEvent, P>{

    //private final XPreProcess prelude;
    //private final XPostProcess postlude;


    XProgramBase(ImmutableList<P> processes) {
        //this.prelude = prelude;
        //this.postlude = postlude;
        super(processes);
    }

    public ImmutableList<P> getProcesses() {
        return getGraphs();
    }


    // TODO: rename in future
    public ImmutableSet<XEvent> getMemEvents() {
        return getNodes(e -> e instanceof XSharedMemoryEvent);
    }

    public int size() {
        int result = 0;
        for (P process : getProcesses()) {
            result += process.size();
        }
        return result;
    }
}