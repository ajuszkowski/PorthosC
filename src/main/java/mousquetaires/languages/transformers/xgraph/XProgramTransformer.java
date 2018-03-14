package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.XProgramBuilder;
import mousquetaires.languages.syntax.xgraph.process.XFlowGraph;


public class XProgramTransformer {

    public static XProgram unroll(XProgram program, int bound) {
        XProgramBuilder builder = new XProgramBuilder();
        for (XFlowGraph process : program.getAllProcesses()) {
            // TODO: fix heap pollution here
            XFlowGraphUnroller unroller = new XFlowGraphUnroller(process, bound); // TODO: pass additional actors here
            unroller.doUnroll();

            builder.addProcess(unroller.getUnrolledGraph());
        }
        builder.markUnrolled();
        return builder.build();
    }
}
