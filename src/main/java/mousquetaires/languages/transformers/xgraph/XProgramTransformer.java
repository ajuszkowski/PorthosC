package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.syntax.xgraph.XCyclicProgram;
import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.XProgramBuilder;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;


public class XProgramTransformer {

    public static XProgram unroll(XCyclicProgram program, int bound) {
        XProgramBuilder builder = new XProgramBuilder();
        for (XCyclicProcess process : program.getProcesses()) {
            XFlowGraphUnroller unroller = new XFlowGraphUnroller(process, bound);
            unroller.doUnroll();
            builder.addProcess(unroller.getUnrolledGraph());
        }
        return builder.build();
    }

}
