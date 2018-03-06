package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.converters.tozformula.helpers.ZDataFlowEncoder;
import mousquetaires.languages.converters.tozformula.helpers.ZOperatorEncoder;
import mousquetaires.languages.syntax.zformula.ZBoolConjunctionBuilder;
import mousquetaires.languages.syntax.zformula.ZBoolFormula;
import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;

// Z3 timeout: https://stackoverflow.com/a/19991250
// (set-option :timeout 10000)

// Scala library for parsing and printing the SMT-LIB format
// https://github.com/regb/scala-smtlib
public class XProgramToZformulaConverter {

    public XProgramToZformulaConverter() {
        // todo: remember timeout
    }

    public ZBoolFormula encode(XProgram program) {
        ZOperatorEncoder operatorEncoder = new ZOperatorEncoder();//ctx);
        ZDataFlowEncoder dataFlowEncoder = new ZDataFlowEncoder(operatorEncoder, program);

        ZBoolConjunctionBuilder programFormula = new ZBoolConjunctionBuilder();
        for (XProcess process : program.getAllProcesses()) {
            XProcessToZformulaConverter processEncoder = new XProcessToZformulaConverter(dataFlowEncoder);
            //ZBoolFormula processFormula = processEncoder.encode(process); //process.accept(processEncoder);
            //programFormula.addSubFormula(processFormula);
        }
        return programFormula.build();
    }
}
