package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;
import mousquetaires.utils.patterns.Builder;


public class XProgramBuilder extends Builder<XProgram> {

    private ImmutableList.Builder<XFlowGraph> processes;

    public XProgramBuilder() {
        this.processes = new ImmutableList.Builder<>();
    }

    public void addProcess(XFlowGraph process) {
        processes.add(process);
    }

    @Override
    public XProgram build() {
        return new XProgram(processes.build());
    }
}
