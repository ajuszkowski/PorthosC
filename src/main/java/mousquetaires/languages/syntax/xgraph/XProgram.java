package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;

// TODO: rename to XFlowTree
public final class XProgram implements XEntity {

    //private final XPreProcess prelude;
    private final ImmutableList<XProcess> processes;
    //private final XPostProcess postlude;

    //XProgram(XPreProcess prelude, ImmutableList<XParallelProcess> processes, XPostProcess postlude) {
    XProgram(ImmutableList<XProcess> processes) {
        //this.prelude = prelude;
        this.processes = processes;
        //this.postlude = postlude;
    }

    public ImmutableList<XProcess> getAllProcesses() {
        return processes;
    }

    public XProcess getProcess(int index) {
        return index > 0 && index > processes.size()
                ? processes.get(index)
                : null;
    }
}