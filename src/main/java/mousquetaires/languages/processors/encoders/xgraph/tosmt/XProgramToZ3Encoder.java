package mousquetaires.languages.processors.encoders.xgraph.tosmt;

import com.microsoft.z3.Context;
import mousquetaires.languages.processors.encoders.xgraph.tosmt.helpers.ZDataFlowEncoder;
import mousquetaires.languages.processors.encoders.xgraph.tosmt.helpers.ZEventNameEncoder;
import mousquetaires.languages.processors.encoders.xgraph.tosmt.helpers.ZOperatorEncoder;
import mousquetaires.languages.syntax.smt.ZBoolMultiFormula;
import mousquetaires.languages.syntax.smt.ZBoolFormulaConjunctionBuilder;
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

    public ZBoolMultiFormula encode(XProgram program) {
        Context ctx = new Context();
        ZEventNameEncoder eventNameEncoder = new ZEventNameEncoder(ctx);
        ZOperatorEncoder operatorEncoder = new ZOperatorEncoder();//ctx);
        ZDataFlowEncoder dataFlowEncoder = new ZDataFlowEncoder(ctx, operatorEncoder, eventNameEncoder);

        ZBoolFormulaConjunctionBuilder programFormula = new ZBoolFormulaConjunctionBuilder(ctx);
        for (XProcess process : program.getAllProcesses()) {
            XProcessToZ3Encoder processEncoder = new XProcessToZ3Encoder(ctx, dataFlowEncoder, eventNameEncoder);
            ZBoolMultiFormula processFormula = processEncoder.encode(process); //process.accept(processEncoder);
            programFormula.addSubFormula(processFormula);
        }
        return programFormula.build();
    }
}
