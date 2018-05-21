package mousquetaires.app.modules.porthos;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
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
import mousquetaires.languages.syntax.zformula.ZFormulaBuilder;
import mousquetaires.languages.transformers.xgraph.XProgramTransformer;
import mousquetaires.memorymodels.wmm.MemoryModel;

import java.io.File;
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
//todo: solving timeout!
            int unrollBound = 16; // TODO: get from options

            MemoryModel.Kind sourceModelKind = options.sourceModel;
            MemoryModel sourceModel = sourceModelKind.createModel();
            MemoryModel.Kind targetModelKind = options.targetModel;
            MemoryModel targetModel = targetModelKind.createModel();

            File inputProgramFile = options.inputProgramFile;
            InputLanguage language = InputExtensions.parseProgramLanguage(inputProgramFile.getName());
            YtreeParser parser = new YtreeParser(inputProgramFile, language);
            YSyntaxTree yTree = parser.parseFile();

            Context ctx = new Context();

            System.out.println("Encoding...");

            XProgram sourceCompiled = compile(yTree, sourceModelKind, unrollBound, ctx);
            XProgram targetCompiled = compile(yTree, targetModelKind, unrollBound, ctx);

            XProgram2ZformulaEncoder sourceEncoder = new XProgram2ZformulaEncoder(ctx, sourceCompiled);
            XProgram2ZformulaEncoder targetEncoder = new XProgram2ZformulaEncoder(ctx, targetCompiled);


            ZFormulaBuilder formulaBuilder = new ZFormulaBuilder(ctx);

            sourceEncoder.encodeProgram(sourceCompiled);//encodeDF + encodeCF + encodeDF_RF + Domain.encode
            //sourceModel.encode(sourceCompiled, ctx, formulaBuilder);//encodeMM
            formulaBuilder.addAssert( sourceCompiled.encodeMM(ctx, sourceModelKind) );
            formulaBuilder.addAssert( sourceCompiled.encodeConsistent(ctx, sourceModelKind) );


            BoolExpr formula = formulaBuilder.build();

            Solver solverSource = ctx.mkSolver();
            Solver solverTarget = ctx.mkSolver();

            //solverSource.add(pTarget.encodeDF(ctx));
            //solverSource.add(pTarget.encodeCF(ctx));
            //solverSource.add(pTarget.encodeDF_RF(ctx));
            //solverSource.add(Domain.encode(pTarget, ctx));
            //solverSource.add(pTarget.encodeMM(ctx, target));
            //solverSource.add(pTarget.encodeConsistent(ctx, target));
            //solverSource.add(sourceDF);
            //solverSource.add(sourceCF);
            //solverSource.add(sourceDF_RF);
            //solverSource.add(sourceDomain);
            //solverSource.add(sourceMM);
            //solverSource.add(pSource.encodeInconsistent(ctx, source));
            //solverSource.add(Encodings.encodeCommonExecutions(pTarget, pSource, ctx));
            //
            //solverTarget.add(sourceDF);
            //solverTarget.add(sourceCF);
            //solverTarget.add(sourceDF_RF);
            //solverTarget.add(sourceDomain);
            //solverTarget.add(sourceMM);
            //solverTarget.add(pSource.encodeConsistent(ctx, source));
            //
            //sourceSolver.add(formula);
            //
            //System.out.println("Solving...");
            //ctx.setPrintMode(Z3_ast_print_mode.Z3_PRINT_SMTLIB_FULL);




        /*
        program.initialize();
        Program pSource = program.clone();
        Program pTarget = program.clone();

        pSource.compile(source, false, true);
        Integer startEId = Collections.max(pSource.buildEvents().stream().filter(e -> e instanceof Init).map(e -> e.getEId()).collect(Collectors.toSet())) + 1;
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
        */

        throw new IOException();//just to make code compilable for a while
        } catch (IOException e) {
            verdict.addError(new IOError(e));
        } catch (Exception e) {
            verdict.addError(new UnrecognisedError(AppError.Severity.Critical, e));
        }

        return verdict;
    }

    private XProgram compile(YSyntaxTree yTree, MemoryModel.Kind memoryModelKind, int unrollBound, Context ctx) {
        Ytree2XgraphConverter yConverter = new Ytree2XgraphConverter(memoryModelKind);
        XCyclicProgram program = yConverter.convert(yTree);
        return XProgramTransformer.unroll(program, unrollBound);
    }
}
