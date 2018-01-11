package mousquetaires.app.modules.dartagnan;

import mousquetaires.app.errors.*;
import mousquetaires.app.modules.AppModule;
import mousquetaires.languages.eventrepr.Programme;
import mousquetaires.languages.eventrepr.ProgrammeConverter;
import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.parsers.YtreeParser;
import mousquetaires.languages.eventrepr.memory.datamodels.DataModel;
import mousquetaires.memorymodels.old.MemoryModel;
import mousquetaires.memorymodels.old.MemoryModelFactory;

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

            MemoryModel mcm = MemoryModelFactory.getMemoryModel(options.sourceModel);

            YSyntaxTree internalRepr = YtreeParser.parse(options.inputProgramFile);

            DataModel dataModel = null; // TODO: pass as cli-option
            Programme programme = ProgrammeConverter.toProgramme(internalRepr, dataModel);

            // SmtEncoder.encode(programme) ...

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

            String target = options.sourceModel.name();
        /*
        programme.initialize();
        programme.compile(target, false, true);

        Context ctx = new Context();
        Solver solver = ctx.mkSolver();

        solver.add(programme.encodeDF(ctx));
        solver.add(programme.ass.encode(ctx));
        solver.add(programme.encodeCF(ctx));
        solver.add(programme.encodeDF_RF(ctx));
        solver.add(Domain.encode(programme, ctx));
        if (mcm != null) {
            log.warning(mcm.write());

            solver.add(mcm.encode(programme, ctx));
            solver.add(mcm.Consistent(programme, ctx));
        } else {
            log.info("Using static model.");
            solver.add(programme.encodeMM(ctx, target));
            solver.add(programme.encodeConsistent(ctx, target));
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
