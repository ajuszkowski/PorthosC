package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.XUnrolledProgram;
import mousquetaires.languages.syntax.xgraph.process.XUnrolledProcess;
import mousquetaires.languages.syntax.zformula.ZBoolConjunctionBuilder;
import mousquetaires.languages.syntax.zformula.ZBoolFormula;

// Z3 timeout: https://stackoverflow.com/a/19991250
// (set-option :timeout 10000)

// Scala library for parsing and printing the SMT-LIB format
// https://github.com/regb/scala-smtlib
public class XProgramToZformulaConverter {

    // TODO: unify names 'encode', 'convert', ...
    public ZBoolFormula encode(XUnrolledProgram program) {
        ZDataFlowEncoder dataFlowEncoder = new ZDataFlowEncoder(program);

        ZBoolConjunctionBuilder programFormula = new ZBoolConjunctionBuilder();
        for (XUnrolledProcess graph : program.getAllProcesses()) {
            XFlowGraphToZformulaConverter processEncoder = new XFlowGraphToZformulaConverter(dataFlowEncoder);
            ZBoolFormula processFormula = processEncoder.encode(graph);
            programFormula.addSubFormula(processFormula);
        }
        return programFormula.build();
    }
}
