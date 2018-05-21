package mousquetaires.app.modules.dartagnan;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.enumerations.Z3_ast_print_mode;
import mousquetaires.app.errors.AppError;
import mousquetaires.app.errors.IOError;
import mousquetaires.app.errors.UnrecognisedError;
import mousquetaires.app.modules.AppModule;
import mousquetaires.languages.InputExtensions;
import mousquetaires.languages.InputLanguage;
import mousquetaires.languages.converters.toxgraph.Ytree2XgraphConverter;
import mousquetaires.languages.converters.toytree.YtreeParser;
import mousquetaires.languages.converters.tozformula.XProgram2ZformulaEncoder;
import mousquetaires.languages.syntax.xgraph.program.XCyclicProgram;
import mousquetaires.languages.syntax.xgraph.program.XProgram;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.transformers.xgraph.XProgramTransformer;
import mousquetaires.memorymodels.wmm.MemoryModel;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;


public class DartagnanModule extends AppModule {

    private static final Logger log = Logger.getLogger(DartagnanModule.class.getName());

    private final DartagnanOptions options;

    public DartagnanModule(DartagnanOptions options) {
        this.options = options;
    }

    @Override
    public DartagnanVerdict run() {

        DartagnanVerdict verdict = new DartagnanVerdict();

        try {
            //todo: solving timeout!
            int unrollBound = 32; // TODO: get from options

            MemoryModel.Kind memoryModelKind = options.sourceModel;
            //MemoryModel memoryModel = memoryModelKind.createModel();

            File inputProgramFile = options.inputProgramFile;
            InputLanguage language = InputExtensions.parseProgramLanguage(inputProgramFile.getName());
            YtreeParser parser = new YtreeParser(inputProgramFile, language);
            YSyntaxTree yTree = parser.parseFile();

            Ytree2XgraphConverter yConverter = new Ytree2XgraphConverter(memoryModelKind);

            verdict.onStartInterpretation();
            XCyclicProgram program = yConverter.convert(yTree);
            verdict.onFinishInterpretation();

            //for (XCyclicProcess process : program.getProcesses()) {
            //    GraphDumper.tryDumpToFile(process, "build/graphs/paper", process.getId().getValue());
            //}

            verdict.onStartUnrolling();
            XProgram unrolledProgram = XProgramTransformer.unroll(program, unrollBound);
            verdict.onFinishUnrolling();

            //for (XProcess process : unrolledProgram.getProcesses()) {
            //    GraphDumper.tryDumpToFile(process, "build/graphs/paper", process.getId().getValue()+"_unrolled");
            //}

            //System.exit(1);

            //for (XProcess process : unrolledProgram.getProcesses()) {
            //    GraphDumper.tryDumpToFile(process, "build/graphs", process.getId().getValue());
            //}
            //System.exit(1);

            verdict.onStartProgramEncoding();

            Context ctx = new Context();
            Solver solver = ctx.mkSolver();

            XProgram2ZformulaEncoder encoder = new XProgram2ZformulaEncoder(ctx, unrolledProgram);

            BoolExpr programFormula = encoder.encodeProgram(unrolledProgram);
            solver.add(programFormula);//encodeDF, getAss().encode(), encodeCF, encodeDF_RF
            //System.out.println(programFormula);

            verdict.onFinishProgramEncoding();

            verdict.onStartModelEncoding();
            //encoder.encodeProgram(unrolledProgram);//encodeDF, getAss().encode(), encodeCF, encodeDF_RF
            solver.add(encoder.Domain_encode(unrolledProgram));//Domain.encode
            solver.add(unrolledProgram.encodeMM(ctx, memoryModelKind));
            solver.add(unrolledProgram.encodeConsistent(ctx, memoryModelKind));

            verdict.onFinishModelEncoding();


            verdict.onStartSolving();
            ctx.setPrintMode(Z3_ast_print_mode.Z3_PRINT_SMTLIB_FULL);
            if (solver.check() == com.microsoft.z3.Status.SATISFIABLE) {
                verdict.result = DartagnanVerdict.Status.Reachable;
            }
            else {
                verdict.result = DartagnanVerdict.Status.NonReachable;
            }
            verdict.onFinishSolving();

            //Encodings.encodeReachedState(unrolledProgram, solver.getModel(), ctx);
        }
        catch (IOException e) {
            verdict.addError(new IOError(e));
        }
        catch (Exception e) {
            verdict.addError(new UnrecognisedError(AppError.Severity.Critical, e));
        }


        return verdict;
    }

}
