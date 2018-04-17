package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.XUnrolledProgram;
import mousquetaires.languages.syntax.xgraph.XUnrolledProgramBuilder;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;


public class XProgramTransformer {

    public static XUnrolledProgram unroll(XProgram program, int bound) {
        XUnrolledProgramBuilder builder = new XUnrolledProgramBuilder();
        for (XCyclicProcess process : program.getProcesses()) {
            XFlowGraphUnroller unroller = new XFlowGraphUnroller(process, bound);
            unroller.doUnroll();
            builder.addProcess(unroller.getUnrolledGraph());
        }
        return builder.build();
    }

}
