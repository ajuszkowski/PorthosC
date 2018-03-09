package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;

public final class XProgram implements XEntity {

    //private final XPreProcess prelude;
    private final ImmutableList<XFlowGraph> processes;
    //private final XPostProcess postlude;

    //XProgram(XPreProcess prelude, ImmutableList<XParallelProcess> process, XPostProcess postlude) {
    XProgram(ImmutableList<XFlowGraph> processes) {
        //this.prelude = prelude;
        this.processes = processes;
        //this.postlude = postlude;
    }

    public ImmutableList<XFlowGraph> getAllProcesses() {
        return processes;
    }

    public XFlowGraph getProcess(int index) {
        return index > 0 && index > processes.size()
                ? processes.get(index)
                : null;
    }
}