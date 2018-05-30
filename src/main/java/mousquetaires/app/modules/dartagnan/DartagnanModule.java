package mousquetaires.app.modules.dartagnan;

import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.enumerations.Z3_ast_print_mode;
import mousquetaires.app.errors.AppError;
import mousquetaires.app.errors.IOError;
import mousquetaires.app.errors.UnrecognisedError;
import mousquetaires.app.modules.AppModule;
import mousquetaires.app.modules.AppVerdict;
import mousquetaires.languages.InputExtensions;
import mousquetaires.languages.InputLanguage;
import mousquetaires.languages.common.graph.render.GraphDumper;
import mousquetaires.languages.converters.toxgraph.Y2XConverter;
import mousquetaires.languages.converters.toytree.YtreeParser;
import mousquetaires.languages.converters.tozformula.XProgram2ZformulaEncoder;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLocalMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XSharedMemoryEvent;
import mousquetaires.languages.syntax.xgraph.process.XCyclicProcess;
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

        DartagnanVerdict verdict = new DartagnanVerdict(options);
        verdict.startAll();

        try {
            //todo: solving timeout!
            int unrollBound = options.unrollingBound; //27;//75;

            MemoryModel.Kind memoryModelKind = options.sourceModel;
            //MemoryModel memoryModel = memoryModelKind.createModel();

            File inputProgramFile = options.inputProgramFile;
            InputLanguage language = InputExtensions.parseProgramLanguage(inputProgramFile.getName());
            YtreeParser parser = new YtreeParser(inputProgramFile, language);
            YSyntaxTree yTree = parser.parseFile();

            Y2XConverter yConverter = new Y2XConverter(memoryModelKind);

            verdict.onStart(AppVerdict.ProgramStage.Interpretation);
            XCyclicProgram program = yConverter.convert(yTree);
            verdict.onFinish(AppVerdict.ProgramStage.Interpretation);

            verdict.setEntitiesNumber(false, XEvent.class, program.getAllEvents().size());
            verdict.setEntitiesNumber(false, XMemoryEvent.class, program.getMemoryEvents().size());
            verdict.setEntitiesNumber(false, XLocalMemoryEvent.class, program.getLocalMemoryEvents().size());
            verdict.setEntitiesNumber(false, XSharedMemoryEvent.class, program.getSharedMemoryEvents().size());
            verdict.setEntitiesNumber(false, XComputationEvent.class, program.getComputationEvents().size());
            verdict.setEntitiesNumber(false, XBarrierEvent.class, program.getBarrierEvents().size());
            verdict.setEntitiesNumber(false, XBarrierEvent.class, program.getBarrierEvents().size());
            verdict.setEntitiesNumber(false, "_XEdgePrimary", program.getEdgesCount(true));
            verdict.setEntitiesNumber(false, "_XEdgeAlternative", program.getEdgesCount(false));

            for (XCyclicProcess process : program.getProcesses()) {
                GraphDumper.tryDumpToFile(process, "build/graphs/paper/test", process.getId().getValue());
            }

            verdict.onStart(AppVerdict.ProgramStage.Unrolling);
            XProgram unrolledProgram = XProgramTransformer.unroll(program, unrollBound);
            verdict.onFinish(AppVerdict.ProgramStage.Unrolling);

            verdict.setEntitiesNumber(true, XEvent.class, unrolledProgram.getAllEvents().size());
            verdict.setEntitiesNumber(true, XMemoryEvent.class, unrolledProgram.getMemoryEvents().size());
            verdict.setEntitiesNumber(true, XLocalMemoryEvent.class, unrolledProgram.getLocalMemoryEvents().size());
            verdict.setEntitiesNumber(true, XSharedMemoryEvent.class, unrolledProgram.getSharedMemoryEvents().size());
            verdict.setEntitiesNumber(true, XComputationEvent.class, unrolledProgram.getComputationEvents().size());
            verdict.setEntitiesNumber(true, XBarrierEvent.class, unrolledProgram.getBarrierEvents().size());
            verdict.setEntitiesNumber(true, "_XEdgePrimary", unrolledProgram.getEdgesCount(true));
            verdict.setEntitiesNumber(true, "_XEdgeAlternative", unrolledProgram.getEdgesCount(false));

            //for (XProcess process : unrolledProgram.getProcesses()) {
            //    GraphDumper.tryDumpToFile(process, "build/graphs/paper", process.getId().getValue()+"_unrolled");
            //}

            //verdict.onStartProgramEncoding();

            Context ctx = new Context();
            Solver solver = ctx.mkSolver();

            XProgram2ZformulaEncoder encoder = new XProgram2ZformulaEncoder(ctx, unrolledProgram);

            verdict.onStart(AppVerdict.ProgramStage.ProgramEncoding);
            solver.add(encoder.encodeProgram(unrolledProgram));//encodeDF, getAss().encode(), encodeCF, encodeDF_RF
            verdict.onFinish(AppVerdict.ProgramStage.ProgramEncoding);

            verdict.onStart(AppVerdict.ProgramStage.ProgramDomainEncoding);
            solver.add(encoder.Domain_encode(unrolledProgram));//Domain.encode
            verdict.onFinish(AppVerdict.ProgramStage.ProgramDomainEncoding);

            verdict.onStart(AppVerdict.ProgramStage.MemoryModelEncoding);
            //encoder.encodeProgram(unrolledProgram);//encodeDF, getAss().encode(), encodeCF, encodeDF_RF
            solver.add(unrolledProgram.encodeMM(ctx, memoryModelKind));
            solver.add(unrolledProgram.encodeConsistent(ctx, memoryModelKind));
            verdict.onFinish(AppVerdict.ProgramStage.MemoryModelEncoding);


            verdict.onStart(AppVerdict.ProgramStage.Solving);
            ctx.setPrintMode(Z3_ast_print_mode.Z3_PRINT_SMTLIB_FULL);
            if (solver.check() == com.microsoft.z3.Status.SATISFIABLE) {
                verdict.result = DartagnanVerdict.Status.Reachable;
            }
            else {
                verdict.result = DartagnanVerdict.Status.NonReachable;
            }
            verdict.onFinish(AppVerdict.ProgramStage.Solving);

            verdict.finishAll();

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
