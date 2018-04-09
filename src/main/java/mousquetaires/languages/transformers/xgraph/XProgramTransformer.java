package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.XUnrolledProgram;
import mousquetaires.languages.syntax.xgraph.XUnrolledProgramBuilder;
import mousquetaires.languages.syntax.xgraph.process.XProcess;


public class XProgramTransformer {

    public static XUnrolledProgram unroll(XProgram program, int bound) {
        XUnrolledProgramBuilder builder = new XUnrolledProgramBuilder();
        for (XProcess process : program.getProcesses()) {
            XFlowGraphUnroller unroller = new XFlowGraphUnroller(process, bound);
            unroller.doUnroll();
            builder.addProcess(unroller.getProcessedGraph());
        }
        return builder.build();
    }

}
