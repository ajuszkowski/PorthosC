package mousquetaires.app.modules.dartagnan;

import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.enumerations.Z3_ast_print_mode;
import mousquetaires.app.modules.IAppModule;
import mousquetaires.app.options.AppOptions;
import mousquetaires.languages.parsers.ProgramParserFactory;
import mousquetaires.program.Program;
import mousquetaires.wmm.Domain;
import mousquetaires.wmm.MemoryModel;
import mousquetaires.wmm.MemoryModelFactory;

import java.io.IOException;
import java.util.logging.Logger;


public class DartagnanModule implements IAppModule {

    private static final Logger log = Logger.getLogger(DartagnanModule.class.getName());

    private final DartagnanOptions options;

    public DartagnanModule(DartagnanOptions options) {
        this.options = options;
    }

    @Override
    public DartagnanVerdict run() throws /*Z3Exception,//--RuntimeException, no need to declare*/ IOException {

        DartagnanVerdict verdict = new DartagnanVerdict();
        verdict.onStartExecution();

        //if (inputFilePath.endsWith("litmus")) {
        //    LitmusLexer lexer = new LitmusLexer(input);
        //    CommonTokenStream tokens = new CommonTokenStream(lexer);
        //    LitmusParser parser = new LitmusParser(tokens);
        //    program = parser.program(inputFilePath).program;
        //}
        //
        //if (inputFilePath.endsWith("pts")) {
        //    PorthosLexer lexer = new PorthosLexer(input);
        //    CommonTokenStream tokens = new CommonTokenStream(lexer);
        //    PorthosParser parser = new PorthosParser(tokens);
        //    program = parser.program(inputFilePath).program;
        //}

        MemoryModel mcm = MemoryModelFactory.getMemoryModel(options.sourceModel);

        Program program = ProgramParserFactory.getProgram(options.inputProgramFile);

        //CommonTree t = (CommonTree) .getTree();

        //Program program = parser.


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
        //    mcm=parser.mcm().value;
        //} else {
        //
        //}

        String target = options.sourceModel.toString();
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

        verdict.onFinishExecution();

        return verdict;
    }

}
