package porthosc.app.modules.dartagnan;

import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.enumerations.Z3_ast_print_mode;
import org.apache.commons.io.FileUtils;
import porthosc.app.errors.AppError;
import porthosc.app.errors.IOError;
import porthosc.app.errors.UnrecognisedError;
import porthosc.app.modules.AppModule;
import porthosc.app.modules.AppVerdict;
import porthosc.languages.common.InputExtensions;
import porthosc.languages.common.InputLanguage;
import porthosc.languages.common.graph.render.GraphDumper;
import porthosc.languages.conversion.toxgraph.Y2XConverter;
import porthosc.languages.conversion.toytree.YtreeParser;
import porthosc.languages.conversion.tozformula.XProgram2ZformulaEncoder;
import porthosc.languages.syntax.xgraph.events.XEvent;
import porthosc.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import porthosc.languages.syntax.xgraph.events.computation.XComputationEvent;
import porthosc.languages.syntax.xgraph.events.controlflow.XControlFlowEvent;
import porthosc.languages.syntax.xgraph.events.fake.XNopEvent;
import porthosc.languages.syntax.xgraph.events.memory.XLocalMemoryEvent;
import porthosc.languages.syntax.xgraph.events.memory.XMemoryEvent;
import porthosc.languages.syntax.xgraph.events.memory.XSharedMemoryEvent;
import porthosc.languages.syntax.xgraph.process.XCyclicProcess;
import porthosc.languages.syntax.xgraph.process.XProcess;
import porthosc.languages.syntax.xgraph.program.XCyclicProgram;
import porthosc.languages.syntax.xgraph.program.XProgram;
import porthosc.languages.syntax.ytree.YSyntaxTree;
import porthosc.languages.conversion.toxgraph.unrolling.XProgramTransformer;
import porthosc.memorymodels.wmm.MemoryModel;
import porthosc.utils.StringUtils;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import static porthosc.utils.StringUtils.wrap;


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

            for (XCyclicProcess p : program.getProcesses()) {
                verdict.addStatistics(false, p, p.getId(), XEvent.class);
                verdict.addStatistics(false, p, p.getId(), XMemoryEvent.class);
                verdict.addStatistics(false, p, p.getId(), XLocalMemoryEvent.class);
                verdict.addStatistics(false, p, p.getId(), XSharedMemoryEvent.class);
                verdict.addStatistics(false, p, p.getId(), XComputationEvent.class);
                verdict.addStatistics(false, p, p.getId(), XBarrierEvent.class);
                verdict.addStatistics(false, p, p.getId(), XControlFlowEvent.class);
                verdict.addStatistics(false, p, p.getId(), XNopEvent.class);
                verdict.setEntitiesNumber(p.getId(), false, "_XEdgePrimary", p.getEdges(true).size());
                verdict.setEntitiesNumber(p.getId(), false, "_XEdgeAlternative", p.getEdges(false).size());
            }

            File dumpDir = options.dumpDirectory;
            if (dumpDir != null) {
                System.out.println("Dumping non-unrolled graphs into directory " + wrap(dumpDir.getAbsolutePath()) + "...");
                if (dumpDir.exists()) {
                    System.out.println("Dump directory exists, cleaning it up");
                    FileUtils.cleanDirectory(dumpDir);
                }
                else if (!dumpDir.mkdirs()) {
                    throw new RuntimeException("Could not create dump directory: " + wrap(dumpDir.getAbsolutePath()));
                }
                System.out.println("Dump directory created: " + wrap(dumpDir.getAbsolutePath()));
                for (XCyclicProcess process : program.getProcesses()) {
                    GraphDumper.tryDumpToFile(process, dumpDir.getAbsolutePath(), process.getId().getValue());
                }
            }

            verdict.onStart(AppVerdict.ProgramStage.Unrolling);
            XProgram unrolledProgram = XProgramTransformer.unroll(program, unrollBound);
            verdict.onFinish(AppVerdict.ProgramStage.Unrolling);

            for (XProcess p : unrolledProgram.getProcesses()) {
                verdict.addStatistics(true, p, p.getId(), XEvent.class);
                verdict.addStatistics(true, p, p.getId(), XMemoryEvent.class);
                verdict.addStatistics(true, p, p.getId(), XLocalMemoryEvent.class);
                verdict.addStatistics(true, p, p.getId(), XSharedMemoryEvent.class);
                verdict.addStatistics(true, p, p.getId(), XComputationEvent.class);
                verdict.addStatistics(true, p, p.getId(), XBarrierEvent.class);
                verdict.addStatistics(true, p, p.getId(), XControlFlowEvent.class);
                verdict.addStatistics(true, p, p.getId(), XNopEvent.class);
                verdict.setEntitiesNumber(p.getId(), true, "_XEdgePrimary", p.getEdges(true).size());
                verdict.setEntitiesNumber(p.getId(), true, "_XEdgeAlternative", p.getEdges(false).size());
            }

            if (dumpDir != null) {
                System.out.println("Dumping unrolled graphs into directory " + wrap(dumpDir.getAbsolutePath()) + "...");
                for (XProcess process : unrolledProgram.getProcesses()) {
                    GraphDumper.tryDumpToFile(process, dumpDir.getAbsolutePath(),
                                              process.getId().getValue() + "_unrolled");
                }
            }


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
