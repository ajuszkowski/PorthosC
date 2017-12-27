package mousquetaires;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import mousquetaires.app.modules.IAppModule;
import mousquetaires.app.modules.porthos.PorthosMode;
import mousquetaires.app.modules.porthos.PorthosOptions;
import mousquetaires.app.modules.porthos.PorthosVerdict;
import mousquetaires.languages.parsers.ProgramParserFactory;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Model;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import com.microsoft.z3.enumerations.Z3_ast_print_mode;

import mousquetaires.program.Init;
import mousquetaires.program.Program;
import mousquetaires.utils.Utils;
import mousquetaires.wmm.Domain;
import mousquetaires.wmm.Encodings;


@SuppressWarnings("deprecation")
public class PorthosModule implements IAppModule {

    private final PorthosOptions options;

    public PorthosModule(PorthosOptions options) {
        this.options = options;
    }

    public PorthosVerdict run() throws /*Z3Exception,//--RuntimeException, no need to declare*/ IOException {

        PorthosVerdict verdict = new PorthosVerdict();
        verdict.onStartExecution();

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
        String source = options.sourceModel.toString().toLowerCase();
        String target = options.targetModel.toString().toLowerCase();
        boolean statePortability = options.mode == PorthosMode.StateInclusion;
        String outputGraphFile = null;
        String[] rels = null;

        //MemoryModel mcm = MemoryModelFactory.getMemoryModel(options.sourceModel);

        Program program = ProgramParserFactory.getProgram(options.inputProgramFile);

        program.initialize();
        Program pSource = program.clone();
        Program pTarget = program.clone();

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
                    Utils.drawGraph(program, pSource, pTarget, ctx, solverSource.getModel(), outputPath, rels);
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
                    //System.out.println("The program is not state-portable");
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

        return verdict;
    }
}
