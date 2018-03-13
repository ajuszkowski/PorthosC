package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;


public final class XProgram implements XEntity {

    //private final XPreProcess prelude;
    private final ImmutableList<XFlowGraph> processes;
    //private final XPostProcess postlude;

    private final boolean isUnrolled;

    //XProgram(XPreProcess prelude, ImmutableList<XParallelProcess> process, XPostProcess postlude) {
    XProgram(ImmutableList<XFlowGraph> processes) {
        this(processes, false);
    }

    XProgram(ImmutableList<XFlowGraph> processes, boolean isUnrolled) {
        //this.prelude = prelude;
        this.processes = processes;
        //this.postlude = postlude;
        this.isUnrolled = isUnrolled;
    }

    public ImmutableList<XFlowGraph> getAllProcesses() {
        return processes;
    }

    public XFlowGraph getProcess(int index) {
        if (index < 0 || index >= processes.size()) {
            throw new IndexOutOfBoundsException(index);
        }
        return processes.get(index);
    }
}