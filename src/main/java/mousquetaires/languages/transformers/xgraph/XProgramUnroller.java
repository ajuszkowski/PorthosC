package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.XProgramBuilder;
import mousquetaires.utils.exceptions.NotImplementedException;


public class XProgramUnroller {

    public static XProgram unroll(XProgram program, int bound) {
        XProgramBuilder builder = new XProgramBuilder();
        throw new NotImplementedException();
        /*XFlowGraphUnroller unroller = new XFlowGraphUnroller(bound);
        for (XFlowGraph process : program.getAllProcesses()) {
            builder.addProcess(unroller.unroll(process));
        }
        builder.markUnrolled();
        return builder.build();*/
    }
}
