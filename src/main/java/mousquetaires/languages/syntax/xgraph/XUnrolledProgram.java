package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.languages.syntax.xgraph.process.XUnrolledProcess;


public final class XUnrolledProgram extends XProgramBase<XUnrolledProcess> {

    XUnrolledProgram(ImmutableList<XUnrolledProcess> processes) {
        super(processes);
    }

}
