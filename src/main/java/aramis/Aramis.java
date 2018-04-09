/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aramis;

/**
 *
 * @author Florian Furbach
 */
import com.microsoft.z3.BoolExpr;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.io.FileUtils;

import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import com.microsoft.z3.Z3Exception;
import com.microsoft.z3.enumerations.Z3_ast_print_mode;

import dartagnan.LitmusLexer;
import dartagnan.LitmusParser;
import dartagnan.PorthosLexer;
import dartagnan.PorthosParser;
import dartagnan.program.Program;
import mousquetaires.memorymodels.Domain;
import mousquetaires.memorymodels.wmm.Wmm;
import mousquetaires.memorymodels.axioms.CandidateAxiom;
import mousquetaires.memorymodels.wmm.CandidateModel;
import mousquetaires.memorymodels.axioms.Consistent;
import mousquetaires.memorymodels.relations.Relation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.cli.*;

@SuppressWarnings("deprecation")
public class Aramis {

    private static final Logger Log = Logger.getLogger(Aramis.class.getName());
    private static CandidateAxiom[] firstCandidates;
    private static CandidateAxiom[] lastNegCandidates;

    private static final ListOfRels candidates = new ListOfRels();
    public static ArrayList<Program> posPrograms;
    public static ArrayList<Program> negPrograms;
    private static int nrOfEvents=0;
    private static ArrayList<Solver> posSolvers;
    private static ArrayList<Solver> negSolvers;
    static final HashMap<Program, Solver> solvers = new HashMap<>();
    protected static final Context ctx = new Context();
    private static int[] current;
    private static Wmm ModelCandidate;
    private static CandidateModel model = new CandidateModel();
    //private static ArrayList<CandidateAxiom> currentAxioms;
    public static Solver solCEGIS;
    public static ArrayList<BoolExpr> negExprs;
    
    private static Program parseProgramFile(String inputFilePath, String target) throws IOException {
        File file = new File(inputFilePath);

        String program = FileUtils.readFileToString(file, "UTF-8");
        ANTLRInputStream input = new ANTLRInputStream(program);

        Program p = new Program(inputFilePath);

        if (inputFilePath.endsWith("litmus")) {
            LitmusLexer lexer = new LitmusLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LitmusParser parser = new LitmusParser(tokens);
            p = parser.program(inputFilePath).p;
        }

        if (inputFilePath.endsWith("pts")) {
            PorthosLexer lexer = new PorthosLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            PorthosParser parser = new PorthosParser(tokens);
            p = parser.program(inputFilePath).p;
        }

        p.initialize();
        p.compile(target, false, true,nrOfEvents);
        nrOfEvents=nrOfEvents+Collections.max(p.getEvents().stream().map(e -> e.getEId()).collect(Collectors.toSet())) + 1;
        return p;
    }
    
    /**
     *
     * The main sketch based synthesis algorithm.
     *
     * @param unchecked index of the first unchecked candidate
     * @param end the the last checked index+1
     * @param currentAxiom the index of the current axiom in current
     * @return
     */
    private static boolean CheckingCandidates(int unchecked, int end, int currentAxiom) {
        //if we have set all candidates
        if (currentAxiom < 0) {
            return checkStaticCurrent();
        }
        //if we have not enough candidates for the axioms left
        if (end - unchecked < currentAxiom + 1) {
            return false;
        }
        int i = unchecked;
        while (i < end) {
            current[currentAxiom] = i;
            if (candidates.get(i).consistent) {
                if (CheckingCandidates(0, i - 1, currentAxiom - 1)) {
                    return true;
                }
            }
            i++;
        }
        return false;
    }

    /**
     * Checks which Negs fail ax and updates the pointer.
     *
     * @param ax
     */
    protected static void computeNegs(CandidateAxiom ax) {
        for (int negPr = 0; negPr < negPrograms.size(); negPr++) {
            Program negProgram = negPrograms.get(negPr);

            //If the test outcome is not known, then compute it
            if (ax.neg[negPr] != Consistent.INCONSISTENT && ax.neg[negPr] != Consistent.CONSISTENT) {
                if (checkCandidate(ax, negProgram)) {
                    ax.neg[negPr] = Consistent.CONSISTENT;
                } else {
                    ax.neg[negPr] = Consistent.INCONSISTENT;
                }
            }

            //if the test fails store ax in the linked list of axioms where the test fails for the dynamic synthesis
            if (ax.neg[negPr] == Consistent.INCONSISTENT) {
                ax.relevant = true;
                if (firstCandidates[negPr] == null) {
                    firstCandidates[negPr] = ax;
                } else {
                    lastNegCandidates[negPr].next[negPr] = ax;
                }
                lastNegCandidates[negPr] = ax;
            }

        }
    }

    public static void main(String[] args) throws Z3Exception, IOException {
        Log.setLevel(Level.ALL);
        //ConsoleHandler handler = new ConsoleHandler();
        ctx.setPrintMode(Z3_ast_print_mode.Z3_PRINT_SMTLIB_FULL);

        // Publish this level
        //handler.setLevel(Level.FINEST);
        //Log.addHandler(handler);
        Log.info("Starting...");

        //Command line options:
        Options options = new Options();

        Option pos = new Option("p", "positive", true, "Directory of program files that should pass the reachability tests");
        pos.setRequired(true);
        options.addOption(pos);

        Option neg = new Option("n", "negative", true, "Directory of program files that should fail the reachability tests");
        neg.setRequired(true);
        options.addOption(neg);

        Option axs = new Option("ax", "axioms", true, "Number of expected Axioms in the model to be synthesized");
        axs.setRequired(false);
        options.addOption(axs);

        Option cegis= new Option("cegis", false, "Using CEGIS");
        cegis.setRequired(false);
        options.addOption(cegis);        
        List<String> MCMs = Arrays.asList("sc", "tso", "pso", "rmo", "alpha", "power", "arm");

        Option targetOpt = new Option("t", "target", true, "target MCM");
        targetOpt.setRequired(true);
        options.addOption(targetOpt);

        Option approxOpt = new Option("approx", false, "Use of approximation encoding of MCM.");
        approxOpt.setRequired(false);
        options.addOption(approxOpt);
        
        CommandLineParser parserCmd = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parserCmd.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Aramis", options);
            System.exit(1);
            return;
        }

        String target = cmd.getOptionValue("target");
        if (!MCMs.stream().anyMatch(mcms -> mcms.trim().equals(target))) {
            System.out.println("Unrecognized target");
            System.exit(0);
            return;
        }

        parsePrograms(new File(cmd.getOptionValue("positive")),new File(cmd.getOptionValue("negative")),target);

        firstCandidates = new CandidateAxiom[negPrograms.size()];
        lastNegCandidates = new CandidateAxiom[negPrograms.size()];

        if(cmd.hasOption("approx")) Relation.Approx=true;
        //Sketch based synthesis:
        if (cmd.hasOption("ax")) {
            int nrOfAxioms = Integer.parseInt(cmd.getOptionValue("ax"));
            current = new int[nrOfAxioms];
            Log.log(Level.FINE, "Axiom: {0}. Pos: {1}. Neg: {2}", new Object[]{nrOfAxioms, posPrograms.size(), negPrograms.size()});
            candidates.addBasicrels();
            boolean temp = false;
            while (!temp) {
                if (CheckingCandidates(candidates.unchecked, candidates.size(), current.length - 1)) {
                    System.out.println("Found Model: " + ModelCandidate.write());
                    Log.log(Level.INFO, "Number of enumerated relations: {0}", candidates.size());
                    temp = true;
                } else {
                    //expand list:
                    candidates.addCandidates(cmd.hasOption("cegis"));
                }
            }
        } //Dynamic synthesis:
        else {
            //currentAxioms = new ArrayList<CandidateAxiom>(negPrograms.size());
            Log.log(Level.WARNING, "Dynamic Synthesis:  Pos: {0}. Neg: {1}", new Object[]{posPrograms.size(), negPrograms.size()});
            if(!cmd.hasOption("cegis")) candidates.addBasicrels();
            boolean temp = false;
            //repeatedly check and expand list:
            while (!temp) {
                Log.warning("Starting Testing Phase...");
                boolean covered=true;
                for (int currentneg = 0; currentneg < negPrograms.size(); currentneg++) {
                    if(firstCandidates[currentneg]==null)covered=false;
                }
                if(covered){
                //use all new relations as potential starting points for dyn. synthesis
                for (int startingpoint = candidates.size() - 1; startingpoint >= candidates.unchecked; startingpoint--) {
                    CandidateAxiom ax = candidates.get(startingpoint);
                    //only use relations that pass all POS and fail at least one NEG
                    if (ax.consistent && ax.relevant) {
                        model.push(ax);
                        Log.fine("Pushing "+ax.write());
                        //try out all relevant models with that relation:
                        if (dynamicSynthesis(startingpoint)) {
                            System.out.println("Found Model: " + model.write());
                            Log.log(Level.INFO, "Number of enumerated relations: {0}", candidates.size());
                            temp = true;
                        } else {
                            //get the stack empty again:
                            model.pop();
                            Log.log(Level.FINE, "Popping {0}", ax.write());
                        }
                    }
                }
                }
                //expand list:
                if (!temp) {
                    candidates.addCandidates(cmd.hasOption("cegis"));
                }
            }

        }
    }

    /**
     *
     * @param startingpoint
     * @return
     */
    private static boolean dynamicSynthesis(int startingpoint) {
        int firstUncovered = model.getNextPassingNeg();
        if (firstUncovered >= negPrograms.size()) {
            return checkDynamicCurrent();
        }
        CandidateAxiom addingax = firstCandidates[firstUncovered];
        while (addingax != null) {
            if (addingax.position >= startingpoint) {
                return false;
            }
            if (!model.redundand(addingax)) {
                model.push(addingax);
                Log.log(Level.FINE, "Pushing {0} on level {1}", new Object[]{addingax.write(), firstUncovered});
                if (dynamicSynthesis(startingpoint)) {
                    return true;
                }
                model.pop();
                Log.log(Level.FINE, "Popping {0} on level {1}", new Object[]{addingax.write(), firstUncovered});
            }
            addingax = addingax.next[firstUncovered];
        }
        return false;
    }

    /**
     * Checks ax for consistency against all progs.
     *
     * @param ax
     * @return true if all pos pass
     */
    protected static boolean checkCandidate(CandidateAxiom ax) {
        for (int i = 0; i < posPrograms.size(); i++) {
            if (ax.pos[i] == Consistent.INCONSISTENT) {
                ax.consistent = false;
                return false;
            }
            if (ax.pos[i] != Consistent.CONSISTENT) {
                if (checkCandidate(ax, posPrograms.get(i))) {
                    ax.pos[i] = Consistent.CONSISTENT;
                } else {
                    ax.pos[i] = Consistent.INCONSISTENT;
                    ax.consistent = false;
                    return false;
                }
            }
        }
        ax.consistent = true;
        computeNegs(ax);
        return true;
    }

    /**
     * Checks ax for consistency against the given prog.
     *
     * @param ax
     * @param p
     * @return
     */
    private static boolean checkCandidate(CandidateAxiom ax, Program p) {
        Wmm tempmodel = new Wmm();
        tempmodel.addAxiom(ax);
        Solver s = solvers.get(p);
        s.push();
        s.add(tempmodel.encode(p, ctx));
        s.add(tempmodel.Consistent(p, ctx));
        Status sat = s.check();
        s.pop();
        return (sat == Status.SATISFIABLE);
    }

    /**
     * Sketch based synthesis: Checks the current model for consistency. 
     * TODO: make sure some preprocessed neg cases get left out.
     *
     * @return
     */
    private static boolean checkStaticCurrent() {
        ModelCandidate = new Wmm();
        for (int i : current) {
            ModelCandidate.addAxiom(candidates.get(i));
        }
        Log.fine("Checking " + ModelCandidate.write());
        for (int i = 0; i < negPrograms.size(); i++) {
            Program p=negPrograms.get(i);
            
            //check if p is already knwon to be inconsistent with one of the axioms, if so we can skip it.
            boolean cons = true;
            for (int j : current) {
                if (candidates.get(j).neg[i] == Consistent.INCONSISTENT) {
                    cons = false;
                }
            }
            if (cons) {
                Log.finer("Checking neg " + p.name);
                Solver s = solvers.get(p);
                s.push();
                s.add(ModelCandidate.encode(p, ctx));
                s.add(ModelCandidate.Consistent(p, ctx));
                Status sat = s.check();
                s.pop();
                if (sat == Status.SATISFIABLE) {
                    return false;
                }
            }

        }
        for (Program p : posPrograms) {
            Log.finer("Checking pos " + p.name);
            Solver s = solvers.get(p);
            s.push();
            s.add(ModelCandidate.encode(p, ctx));
            s.add(ModelCandidate.Consistent(p, ctx));
            Status sat = s.check();
            s.pop();
            if (sat != Status.SATISFIABLE) {
                return false;
            }

        }
        return true;
    }

    /**
     * Dynamic synthesis: Checks the current model for consistency.
     *
     * @return true if all POS tests pass.
     */
    private static boolean checkDynamicCurrent() {
        Log.log(Level.FINE, "Checking {0}", model.write());
        if(model.size()==1) return true;
        for (Program p : posPrograms) {
            Log.log(Level.FINER, "Checking pos {0}", p.name);
            Solver s = solvers.get(p);
            s.push();
            s.add(model.encode(p, ctx));
            s.add(model.Consistent(p, ctx));
            Status sat = s.check();
            s.pop();
            if (sat != Status.SATISFIABLE) {
                return false;
            }

        }
        return true;
    }

    private static void parsePrograms(File positiveDir, File negativeDir, String target) throws IOException {
        //parse pos tests
        posPrograms = new ArrayList<>(positiveDir.listFiles().length);
        posSolvers = new ArrayList<>(positiveDir.listFiles().length);
        solCEGIS=ctx.mkSolver();
        for (File listFile : positiveDir.listFiles()) {
            String string = listFile.getPath();

            if (!string.endsWith("pts") && !string.endsWith("litmus")) {
                Log.warning("Unrecognized program format for " + string);

            } else {
                Log.fine("Positive litmus test: " + string);
                Program p = parseProgramFile(string, target);
                posPrograms.add(p);
                solvers.put(p, ctx.mkSolver());
                Solver s = solvers.get(p);
                BoolExpr temp=p.encodeDF(ctx);
                temp=ctx.mkAnd(temp,p.ass.encode(ctx));
                temp=ctx.mkAnd(p.encodeCF(ctx));
                temp=ctx.mkAnd(p.encodeDF_RF(ctx));
                temp=ctx.mkAnd(Domain.encode(p, ctx));
                s.add(temp);                
                solCEGIS.add(temp);
            }

        }

        //parse neg tests
        negPrograms = new ArrayList<>(negativeDir.listFiles().length);
        for (File listFile : negativeDir.listFiles()) {
            String string = listFile.getPath();
            if (!string.endsWith("pts") && !string.endsWith("litmus")) {
                Log.warning("Unrecognized program format for " + string);

            } else {
                Log.fine("Negative litmus test: " + string);
                Program p = parseProgramFile(string, target);
                negPrograms.add(p);
                solvers.put(p, ctx.mkSolver());
                Solver s = solvers.get(p);
                BoolExpr temp=p.encodeDF(ctx);
                temp=ctx.mkAnd(temp,p.ass.encode(ctx));
                temp=ctx.mkAnd(p.encodeCF(ctx));
                temp=ctx.mkAnd(p.encodeDF_RF(ctx));
                temp=ctx.mkAnd(Domain.encode(p, ctx));
                s.add(temp); 
                //negExprs.add(temp);
            }

        }    }
}
