package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.process.XUnrolledProcess;
import mousquetaires.utils.patterns.BuilderBase;


public class XUnrolledProgramBuilder extends BuilderBase<XUnrolledProgram> {

    private ImmutableList.Builder<XUnrolledProcess> processes;

    public XUnrolledProgramBuilder() {
        this.processes = new ImmutableList.Builder<>();
    }

    public void addProcess(XUnrolledProcess process) {
        processes.add(process);
    }

    @Override
    public XUnrolledProgram build() {
        return new XUnrolledProgram(processes.build());
    }
}
