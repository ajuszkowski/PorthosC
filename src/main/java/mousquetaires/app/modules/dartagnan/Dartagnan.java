package mousquetaires.app.modules.dartagnan;

import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.enumerations.Z3_ast_print_mode;
import mousquetaires.app.modules.AppModule;
import mousquetaires.languages.parsers.PorthosLexer;
import mousquetaires.languages.parsers.PorthosParser;
import mousquetaires.app.options.AppOptions;
import mousquetaires.program.Program;
import mousquetaires.utils.io.FileUtils;
import mousquetaires.wmm.Domain;
import mousquetaires.wmm.MemoryModel;
import mousquetaires.wmm.MemoryModelFactory;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.logging.Logger;


public class Dartagnan extends AppModule {

    private static final Logger log = Logger.getLogger(Dartagnan.class.getName());

    public Dartagnan(AppOptions options) {
        super(options);
    }

    @Override
    public DartagnanVerdict run() { // throws /*Z3Exception,*/ IOException {

        DartagnanVerdict verdict = new DartagnanVerdict();
        verdict.onStartExecution();

        //if (inputFilePath.endsWith("litmus")) {
        //    LitmusLexer lexer = new LitmusLexer(input);
        //    CommonTokenStream tokens = new CommonTokenStream(lexer);
        //    LitmusParser parser = new LitmusParser(tokens);
        //    p = parser.program(inputFilePath).p;
        //}
        //
        //if (inputFilePath.endsWith("pts")) {
        //    PorthosLexer lexer = new PorthosLexer(input);
        //    CommonTokenStream tokens = new CommonTokenStream(lexer);
        //    PorthosParser parser = new PorthosParser(tokens);
        //    p = parser.program(inputFilePath).p;
        //}

        MemoryModel mcm = MemoryModelFactory.getMemoryModel(options.sourceModel);

        CharStream charStream = null;
        try {
            charStream = FileUtils.getFileCharStream(options.inputProgramFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PorthosLexer lexer = new PorthosLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        PorthosParser parser = new PorthosParser(tokenStream);        //InputProgramParserFactory.getParser(options.inputProgramFile);
        Program p = parser.program(options.inputProgramFile.getName()).p;

        //CommonTree t = (CommonTree) .getTree();

        //Program p = parser.


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
        p.initialize();
        p.compile(target, false, true);

        Context ctx = new Context();
        Solver s = ctx.mkSolver();

        s.add(p.encodeDF(ctx));
        s.add(p.ass.encode(ctx));
        s.add(p.encodeCF(ctx));
        s.add(p.encodeDF_RF(ctx));
        s.add(Domain.encode(p, ctx));
        if (mcm != null) {
            log.warning(mcm.write());

            s.add(mcm.encode(p, ctx));
            s.add(mcm.Consistent(p, ctx));
        } else {
            log.info("Using static model.");
            s.add(p.encodeMM(ctx, target));
            s.add(p.encodeConsistent(ctx, target));
        }

        ctx.setPrintMode(Z3_ast_print_mode.Z3_PRINT_SMTLIB_FULL);


        if (s.check() == com.microsoft.z3.Status.SATISFIABLE) {
            verdict.result = DartagnanVerdict.Status.NonReachable;
        } else {
            verdict.result = DartagnanVerdict.Status.Reachable;
        }

        verdict.onFinishExecution();

        return verdict;
    }

}
