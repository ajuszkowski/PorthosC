package mousquetaires.app.modules.porthos;

import mousquetaires.app.errors.AppError;
import mousquetaires.app.errors.IOError;
import mousquetaires.app.errors.UnrecognisedError;
import mousquetaires.app.modules.AppModule;
import mousquetaires.languages.eventrepr.Programme;
import mousquetaires.languages.eventrepr.ProgrammeConverter;
import mousquetaires.languages.eventrepr.memory.datamodels.DataModel;
import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.parsers.YtreeParser;

import java.io.IOException;

//import mousquetaires.program.Init;


@SuppressWarnings("deprecation")
public class PorthosModule extends AppModule {

    private final PorthosOptions options;

    public PorthosModule(PorthosOptions options) {
        this.options = options;
    }

    @Override
    public PorthosVerdict run() {

        PorthosVerdict verdict = new PorthosVerdict();
        verdict.onStartExecution();

        try {

            //options.addOption("state", false, "PORTHOS performs state portability");

            //options.addOption(Option.builder("draw")
            //        .hasArg()
            //        .desc("If a buf is found, it outputs a graph \\path_to_file.dot")
            //        .build());

            //options.addOption(Option.builder("rels")
            //        .hasArgs()
            //        .desc("Relations to be drawn in the graph")
            //        .build());


            //boolean statePortability = cmd.hasOption("state");
            //String[] rels = new String[100];
            //if(cmd.hasOption("rels")) {
            //    rels = cmd.getOptionValues("rels");
            //}


            // MOCKS:
            String source = options.sourceModel.name().toLowerCase();
            String target = options.targetModel.name().toLowerCase();
            boolean statePortability = options.mode == PorthosMode.StateInclusion;
            String outputGraphFile = null;
            String[] rels = null;

            //MemoryModel mcm = MemoryModelFactory.getMemoryModel(options.sourceModel);

            YSyntaxTree internalRepr = YtreeParser.parse(options.inputProgramFile);
            DataModel dataModel = null; // TODO
            Programme programme = ProgrammeConverter.toProgramme(internalRepr, dataModel);


        /*
        programme.initialize();
        Programme pSource = programme.clone();
        Programme pTarget = programme.clone();

        pSource.compile(source, false, true);
        Integer startEId = Collections.max(pSource.getEvents().stream().filter(e -> e instanceof Init).map(e -> e.getEId()).collect(Collectors.toSet())) + 1;
        pTarget.compile(target, false, true, startEId);



        Context ctx = new Context();
        ctx.setPrintMode(Z3_ast_print_mode.Z3_PRINT_SMTLIB_FULL);
        Solver solverSource = ctx.mkSolver();
        Solver solverTarget = ctx.mkSolver();

        BoolExpr sourceDF = pSource.encodeDF(ctx);
        BoolExpr sourceCF = pSource.encodeCF(ctx);
        BoolExpr sourceDF_RF = pSource.encodeDF_RF(ctx);
        BoolExpr sourceDomain = Domain.encode(pSource, ctx);
        BoolExpr sourceMM = pSource.encodeMM(ctx, source);

        solverSource.add(pTarget.encodeDF(ctx));
        solverSource.add(pTarget.encodeCF(ctx));
        solverSource.add(pTarget.encodeDF_RF(ctx));
        solverSource.add(Domain.encode(pTarget, ctx));
        solverSource.add(pTarget.encodeMM(ctx, target));
        solverSource.add(pTarget.encodeConsistent(ctx, target));
        solverSource.add(sourceDF);
        solverSource.add(sourceCF);
        solverSource.add(sourceDF_RF);
        solverSource.add(sourceDomain);
        solverSource.add(sourceMM);
        solverSource.add(pSource.encodeInconsistent(ctx, source));
        solverSource.add(Encodings.encodeCommonExecutions(pTarget, pSource, ctx));

        solverTarget.add(sourceDF);
        solverTarget.add(sourceCF);
        solverTarget.add(sourceDF_RF);
        solverTarget.add(sourceDomain);
        solverTarget.add(sourceMM);
        solverTarget.add(pSource.encodeConsistent(ctx, source));

        if(!statePortability) {
            if(solverSource.check() == Status.SATISFIABLE) {
                verdict.result = PorthosVerdict.Status.NonStatePortable;
                if(outputGraphFile != null) {
                    String outputPath = outputGraphFile;
                    Utils.drawGraph(programme, pSource, pTarget, ctx, solverSource.getModel(), outputPath, rels);
                }
            }
            else {
                verdict.result = PorthosVerdict.Status.StatePortable;
            }
            return verdict;
        }

        int iterations = 0;
        Status lastCheck = Status.SATISFIABLE;
        Set<Expr> visited = new HashSet<>();

        while(lastCheck == Status.SATISFIABLE) {

            lastCheck = solverSource.check();
            if(lastCheck == Status.SATISFIABLE) {
                iterations = iterations + 1;
                Model model = solverSource.getModel();
                solverTarget.push();
                BoolExpr reachedState = Encodings.encodeReachedState(pTarget, model, ctx);
                visited.add(reachedState);
                assert(iterations == visited.size());
                solverTarget.add(reachedState);
                if(solverTarget.check() == Status.UNSATISFIABLE) {
                    //System.out.println("The programme is not state-portable");
                    verdict.iterations = iterations;
                    verdict.result = PorthosVerdict.Status.NonStatePortable;
                    return verdict;
                }
                else {
                    solverTarget.pop();
                    solverSource.add(ctx.mkNot(reachedState));
                }
            }
            else {
                verdict.iterations = iterations;
                verdict.result = PorthosVerdict.Status.StatePortable;
                return verdict;
            }
        }
        */

        } catch (IOException e) {
            verdict.addError(new IOError(e));
        } catch (Exception e) {
            verdict.addError(new UnrecognisedError(AppError.Severity.Critical, e));
        }

        return verdict;
    }
}
