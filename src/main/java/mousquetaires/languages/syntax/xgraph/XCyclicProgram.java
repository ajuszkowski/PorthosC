package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;


public final class XCyclicProgram extends XProgramBase<XCyclicProcess> {

    XCyclicProgram(ImmutableList<XCyclicProcess> processes) {
        super(processes);
    }
}
