package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;
import mousquetaires.utils.patterns.BuilderBase;


public class XProgramBuilder extends BuilderBase<XProgram> {

    private ImmutableList.Builder<XCyclicProcess> processes;

    public XProgramBuilder() {
        this.processes = new ImmutableList.Builder<>();
    }

    public void addProcess(XCyclicProcess process) {
        processes.add(process);
    }

    @Override
    public XProgram build() {
        return new XProgram(processes.build());
    }
}
