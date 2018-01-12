package mousquetaires.app.modules.dartagnan;

import mousquetaires.app.errors.AppError;
import mousquetaires.app.errors.IOError;
import mousquetaires.app.errors.UnrecognisedError;
import mousquetaires.app.modules.AppModule;
import mousquetaires.interpretation.eventrepr.Interpreter;
import mousquetaires.languages.ProgramExtensions;
import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.parsers.YtreeParser;
import mousquetaires.languages.transformers.ytree.YtreeToXreprConverter;
import mousquetaires.languages.xrepr.XProgram;
import mousquetaires.languages.xrepr.memory.datamodels.DataModel;
import mousquetaires.languages.xrepr.memory.datamodels.DataModelLP64;
import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.memorymodels.old.MemoryModel;
import mousquetaires.memorymodels.old.MemoryModelFactory;

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

            MemoryModel mcm = MemoryModelFactory.getMemoryModel(options.sourceModel);
            File inputProgramFile = options.inputProgramFile;
            ProgramLanguage language = ProgramExtensions.parseProgramLanguage(inputProgramFile.getName());
            YSyntaxTree internalRepr = YtreeParser.parse(inputProgramFile, language);
            DataModel dataModel = new DataModelLP64(); // TODO: pass as cli-option
            Interpreter interpreter = new Interpreter(language, dataModel);

            YtreeToXreprConverter converter = new YtreeToXreprConverter(interpreter);
            XProgram program = converter.convert(internalRepr);

            // SmtEncoder.encode(program) ...

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
