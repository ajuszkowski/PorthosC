package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;
import mousquetaires.utils.patterns.BuilderBase;


public class XCyclicProgramBuilder extends BuilderBase<XCyclicProgram> {

    private ImmutableList.Builder<XCyclicProcess> processes;

    public XCyclicProgramBuilder() {
        this.processes = new ImmutableList.Builder<>();
    }

    public void addProcess(XCyclicProcess process) {
        processes.add(process);
    }

    @Override
    public XCyclicProgram build() {
        return new XCyclicProgram(processes.build());
    }
}
