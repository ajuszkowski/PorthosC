package mousquetaires.languages.processors.encoders.xgraph.tosmt;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import mousquetaires.languages.processors.encoders.xgraph.tosmt.helpers.XDataFlowEncoder;
import mousquetaires.languages.processors.encoders.xgraph.tosmt.helpers.XEventNameEncoder;
import mousquetaires.languages.processors.encoders.xgraph.tosmt.helpers.XOperatorEncoder;
import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;


// Z3 timeout: https://stackoverflow.com/a/19991250
// (set-option :timeout 10000)

// Scala library for parsing and printing the SMT-LIB format
// https://github.com/regb/scala-smtlib
public class XProgramToZ3Encoder {

    public XProgramToZ3Encoder() {
        // todo: remember timeout
    }

    public BoolExpr encode(XProgram program) {
        Context ctx = new Context();
        XEventNameEncoder eventNameEncoder = new XEventNameEncoder(ctx);
        XOperatorEncoder operatorEncoder = new XOperatorEncoder(ctx);
        XDataFlowEncoder dataFlowEncoder = new XDataFlowEncoder(ctx, operatorEncoder, eventNameEncoder);

        ConjunctiveFormulaBuilder programFormula = new ConjunctiveFormulaBuilder(ctx);
        for (XProcess process : program.getAllProcesses()) {
            XProcessToZ3Encoder processEncoder = new XProcessToZ3Encoder(ctx, dataFlowEncoder, eventNameEncoder);
            BoolExpr processFormula = processEncoder.encode(process); //process.accept(processEncoder);
            programFormula.addConjunct(processFormula);
        }
        return programFormula.build();
    }
}
