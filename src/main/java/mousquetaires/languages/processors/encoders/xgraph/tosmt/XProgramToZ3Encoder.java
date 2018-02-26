package mousquetaires.languages.processors.encoders.xgraph.tosmt;

import mousquetaires.languages.processors.encoders.xgraph.tosmt.helpers.ZDataFlowEncoder;
import mousquetaires.languages.processors.encoders.xgraph.tosmt.helpers.ZOperatorEncoder;
import mousquetaires.languages.syntax.smt.ZBoolConjunctionBuilder;
import mousquetaires.languages.syntax.smt.ZBoolFormula;
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

    public ZBoolFormula encode(XProgram program) {
        ZOperatorEncoder operatorEncoder = new ZOperatorEncoder();//ctx);
        ZDataFlowEncoder dataFlowEncoder = new ZDataFlowEncoder(operatorEncoder, program);

        ZBoolConjunctionBuilder programFormula = new ZBoolConjunctionBuilder();
        for (XProcess process : program.getAllProcesses()) {
            XProcessToZ3Encoder processEncoder = new XProcessToZ3Encoder(dataFlowEncoder);
            ZBoolFormula processFormula = processEncoder.encode(process); //process.accept(processEncoder);
            programFormula.addSubFormula(processFormula);
        }
        return programFormula.build();
    }
}
