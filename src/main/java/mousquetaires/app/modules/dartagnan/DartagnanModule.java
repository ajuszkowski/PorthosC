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
import mousquetaires.languages.common.citation.CodeCitationService;
import mousquetaires.languages.common.graph.render.GraphDumper;
import mousquetaires.languages.converters.toxgraph.Ytree2XgraphConverter;
import mousquetaires.languages.converters.InputParserBase;
import mousquetaires.languages.converters.toytree.YtreeParser;
import mousquetaires.languages.converters.tozformula.XProgram2ZformulaEncoder;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModel;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModelLP64;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.languages.syntax.xgraph.program.XCyclicProgram;
import mousquetaires.languages.syntax.xgraph.program.XProgram;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.zformula.ZFormulaBuilder;
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
        verdict.onStartExecution();

        try {
            //todo: solving timeout!
            int unrollBound = 16; // TODO: get from options
            DataModel dataModel = new DataModelLP64(); // TODO: pass as cli-option

            MemoryModel.Kind memoryModelKind = options.sourceModel;
            MemoryModel memoryModel = memoryModelKind.createModel();

            File inputProgramFile = options.inputProgramFile;
            InputLanguage language = InputExtensions.parseProgramLanguage(inputProgramFile.getName());
            YtreeParser parser = new YtreeParser(inputProgramFile, language);
            YSyntaxTree yTree = parser.parseFile();

            CodeCitationService citationService = parser.getCitationService();

            Ytree2XgraphConverter yConverter = new Ytree2XgraphConverter(language, memoryModelKind, dataModel);
            XCyclicProgram program = yConverter.convert(yTree);

            for (XCyclicProcess process : program.getProcesses()) {
                GraphDumper.tryDumpToFile(process, "build/graphs/paper", process.getId().getValue());
            }

            XProgram unrolledProgram = XProgramTransformer.unroll(program, unrollBound);

            for (XProcess process : unrolledProgram.getProcesses()) {
                GraphDumper.tryDumpToFile(process, "build/graphs/paper", process.getId().getValue()+"_unrolled");
            }

            System.exit(1);

            Context ctx = new Context();

            XProgram2ZformulaEncoder encoder = new XProgram2ZformulaEncoder(ctx, unrolledProgram);

            //for (XProcess process : unrolledProgram.getProcesses()) {
            //    GraphDumper.tryDumpToFile(process, "build/graphs", process.getId().getValue());
            //}
            //System.exit(1);

            System.out.println("Encoding...");
            ZFormulaBuilder formulaBuilder = new ZFormulaBuilder(ctx);

            encoder.encode(unrolledProgram, formulaBuilder);
            memoryModel.encode(unrolledProgram, ctx, formulaBuilder);
            memoryModel.Consistent(unrolledProgram, ctx, formulaBuilder);

            Solver solver = ctx.mkSolver();
            BoolExpr formula = formulaBuilder.build();

            solver.add(formula);

            System.out.println("Solving...");
            ctx.setPrintMode(Z3_ast_print_mode.Z3_PRINT_SMTLIB_FULL);

            if (solver.check() == com.microsoft.z3.Status.SATISFIABLE) {
                verdict.result = DartagnanVerdict.Status.NonReachable;
            }
            else {
                verdict.result = DartagnanVerdict.Status.Reachable;
            }

            // TODO: cat-file parsing
            //if (cmd.hasOption("file")) {
            //    String filePath = cmd.getOptionValue("file");
            //    if (!filePath.endsWith("cat")) {
            //        System.out.println("Unrecognized memory model format");
            //        System.exit(0);
            //        return;
            //    }
            //    File modelfile = new File(filePath);
            //
            //    String mcmtext = FileUtils.readFileToString(modelfile, "UTF-8");
            //    ANTLRInputStream mcminput = new ANTLRInputStream(mcmtext);
            //    ModelLexer lexer = new ModelLexer(mcminput);
            //    CommonTokenStream tokens = new CommonTokenStream(lexer);
            //    ModelParser parser = new ModelParser(tokens);
            //    //System.out.println((parser.mcm()).getText());
            //    //System.out.println(parser.mcm());
            //    mcm=parser.mcm().bitness;
            //} else {
            //
            //}

        /*
        program.initialize();
        program.compile(target, false, true);

        Context ctx = new Context();
        Solver solver = ctx.mkSolver();

        solver.add(program.encodeDF(ctx));
        solver.add(program.ass.encode(ctx));
        solver.add(program.encodeCF(ctx));
        solver.add(program.encodeDF_RF(ctx));
        solver.add(Domain.encode(program, ctx));
        if (mcm != null) {
            log.warning(mcm.write());

            solver.add(mcm.encode(program, ctx));
            solver.add(mcm.Consistent(program, ctx));
        } else {
            log.info("Using static model.");
            solver.add(program.encodeMM(ctx, target));
            solver.add(program.encodeConsistent(ctx, target));
        }

        ctx.setPrintMode(Z3_ast_print_mode.Z3_PRINT_SMTLIB_FULL);


        if (solver.check() == com.microsoft.z3.Status.SATISFIABLE) {
            verdict.result = DartagnanVerdict.Status.NonReachable;
        } else {
            verdict.result = DartagnanVerdict.Status.Reachable;
        }

        */
        }
        catch (IOException e) {
            verdict.addError(new IOError(e));
        }
        catch (Exception e) {
            verdict.addError(new UnrecognisedError(AppError.Severity.Critical, e));
        }

        verdict.onFinishExecution();

        return verdict;
    }

}
