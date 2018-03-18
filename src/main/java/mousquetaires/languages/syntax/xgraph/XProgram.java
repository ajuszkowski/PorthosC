package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.process.XProcess;


public final class XProgram extends XProgramBase<XProcess> {

    XProgram(ImmutableList<XProcess> processes) {
        super(processes);
    }
}
