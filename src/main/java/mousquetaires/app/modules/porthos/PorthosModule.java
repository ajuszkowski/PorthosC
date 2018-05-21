package mousquetaires.app.modules.porthos;

import com.microsoft.z3.*;
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
import mousquetaires.memorymodels.Encodings;
import mousquetaires.memorymodels.wmm.MemoryModel;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
        verdict.onStartProgramEncoding();

        try {
//todo: solving timeout!
            int unrollBound = 16; // TODO: get from options

            MemoryModel.Kind source = options.sourceModel;
            MemoryModel.Kind target = options.targetModel;

            File inputProgramFile = options.inputProgramFile;
            InputLanguage language = InputExtensions.parseProgramLanguage(inputProgramFile.getName());
            YtreeParser parser = new YtreeParser(inputProgramFile, language);
            YSyntaxTree yTree = parser.parseFile();

            Context ctx = new Context();

            verdict.onStartProgramEncoding();

            XProgram pSource = compile(yTree, source, unrollBound, ctx);
            XProgram pTarget = compile(yTree, target, unrollBound, ctx);

            XProgram2ZformulaEncoder sourceEncoder = new XProgram2ZformulaEncoder(ctx, pSource);
            XProgram2ZformulaEncoder targetEncoder = new XProgram2ZformulaEncoder(ctx, pTarget);


            ctx.setPrintMode(Z3_ast_print_mode.Z3_PRINT_SMTLIB_FULL);
            Solver s = ctx.mkSolver();
            Solver s2 = ctx.mkSolver();

            BoolExpr sourceEnc = sourceEncoder.encodeProgram(pSource);
            BoolExpr sourceDomain = sourceEncoder.Domain_encode(pSource);
            BoolExpr sourceMM = pSource.encodeMM(ctx, source);

            s.add(targetEncoder.encodeProgram(pTarget));
            s.add(targetEncoder.Domain_encode(pTarget));
            s.add(pTarget.encodeMM(ctx, target));
            s.add(pTarget.encodeConsistent(ctx, target));
            s.add(sourceEnc);
            s.add(sourceDomain);
            s.add(sourceMM);
            s.add(Encodings.encodeCommonExecutions(pTarget, pSource, ctx));
            s.add(pSource.encodeInconsistent(ctx, source));

            s2.add(sourceEnc);
            s2.add(sourceDomain);
            s2.add(sourceMM);
            s2.add(pSource.encodeConsistent(ctx, source));

            verdict.onFinishProgramEncoding();

            verdict.onStartSolving();

            if(options.mode == PorthosMode.ExecutionInslusion) {
                if(s.check() == Status.SATISFIABLE) {
                    verdict.result = PorthosVerdict.Status.NonExecutionPortable;
                    //if(outputGraphFile != null) {
                    //    String outputPath = outputGraphFile;
                    //    Utils.drawGraph(program, pSource, pTarget, ctx, s.getModel(), outputPath, rels);
                    //}
                }
                else {
                    verdict.result = PorthosVerdict.Status.ExecutionPortable;
                }
                return verdict;
            }

            int iterations = 0;
            Status lastCheck = Status.SATISFIABLE;
            Set<Expr> visited = new HashSet<>();

            while(lastCheck == Status.SATISFIABLE) {

                lastCheck = s.check();
                if(lastCheck == Status.SATISFIABLE) {
                    iterations = iterations + 1;
                    Model model = s.getModel();
                    s2.push();
                    BoolExpr reachedState = Encodings.encodeReachedState(pTarget, model, ctx);
                    visited.add(reachedState);
                    assert(iterations == visited.size());
                    s2.add(reachedState);
                    if(s2.check() == Status.UNSATISFIABLE) {
                        //System.out.println("The program is not state-portable");
                        verdict.iterations = iterations;
                        verdict.result = PorthosVerdict.Status.NonStatePortable;
                        verdict.onFinishSolving();
                        return verdict;
                    }
                    else {
                        s2.pop();
                        s.add(ctx.mkNot(reachedState));
                    }
                }
                else {
                    verdict.iterations = iterations;
                    verdict.result = PorthosVerdict.Status.StatePortable;
                    verdict.onFinishSolving();
                    return verdict;
                }
            }

            verdict.onFinishSolving();

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
