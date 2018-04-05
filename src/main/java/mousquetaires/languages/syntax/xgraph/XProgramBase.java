package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.syntax.xgraph.events.XEvent;

import java.util.HashSet;
import java.util.Set;


public abstract class XProgramBase<P extends FlowGraph<XEvent>> {

    //private final XPreProcess prelude;
    private final ImmutableList<P> processes;
    //private final XPostProcess postlude;

    XProgramBase(ImmutableList<P> processes) {
        //this.prelude = prelude;
        this.processes = processes;
        //this.postlude = postlude;
    }

    public ImmutableList<P> getAllProcesses() {
        return processes;
    }


    ////TODO: old-code method, to be replaced
    //public Set<XEvent> getEvents() {
    //    Set<XEvent> ret = new HashSet<>();
    //    for (P process : getAllProcesses()) {
    //        ret.addAll(process.getAllEvents());
    //    }
    //    return ret;
    //}

    public int size() {
        int result = 0;
        for (P process : processes) {
            result += process.size();
        }
        return result;
    }
}