package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;


public final class XProgram extends XProgramBase<XCyclicProcess> {

    XProgram(ImmutableList<XCyclicProcess> processes) {
        super(processes);
    }
}
