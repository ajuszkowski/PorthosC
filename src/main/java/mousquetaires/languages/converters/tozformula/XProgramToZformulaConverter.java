package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.XUnrolledProgram;
import mousquetaires.languages.syntax.xgraph.process.XUnrolledProcess;
import mousquetaires.languages.syntax.zformula.ZLogicalConjunctionBuilder;
import mousquetaires.languages.syntax.zformula.ZLogicalFormula;

// Z3 timeout: https://stackoverflow.com/a/19991250
// (set-option :timeout 10000)

// Scala library for parsing and printing the SMT-LIB format
// https://github.com/regb/scala-smtlib
public class XProgramToZformulaConverter {

    // TODO: unify names 'encode', 'convert', ...
    public ZLogicalFormula encode(XUnrolledProgram program) {
        ZDataFlowEncoder dataFlowEncoder = new ZDataFlowEncoder(program);

        ZLogicalConjunctionBuilder programFormula = new ZLogicalConjunctionBuilder();
        for (XUnrolledProcess graph : program.getAllProcesses()) {
            XFlowGraphToZformulaConverter processEncoder = new XFlowGraphToZformulaConverter(dataFlowEncoder);
            ZLogicalFormula processFormula = processEncoder.encode(graph);
            programFormula.addSubFormula(processFormula);
        }
        return programFormula.build();
    }
}
