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
import dartagnan.wmm.Domain;
import dartagnan.wmm.Wmm;
import dartagnan.wmm.CandidateAxiom;
import dartagnan.wmm.CandidateModel;
import dartagnan.wmm.Consistent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.*;

@SuppressWarnings("deprecation")
public class Aramis {

    private static final Logger Log = Logger.getLogger(Aramis.class.getName());
    private static CandidateAxiom[] firstCandidates;
    private static CandidateAxiom[] lastNegCandidates;

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
        p.compile(target, false, true);
        return p;
    }
    private static final ListOfRels candidates = new ListOfRels();
    public static ArrayList<Program> posPrograms;
    public static ArrayList<Program> negPrograms;
    private static ArrayList<Solver> posSolvers;
    private static ArrayList<Solver> negSolvers;
    private static final HashMap<Program, Solver> solvers = new HashMap<>();
    private static final Context ctx = new Context();
    private static int[] current;
    private static Wmm ModelCandidate;
    private static CandidateModel model=new CandidateModel();
    //private static ArrayList<CandidateAxiom> currentAxioms;

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
        boolean temp = false;
        int i = unchecked;
        while (i < end && !temp) {
            current[currentAxiom] = i;
            if (candidates.get(i).consistent) {
                if (CheckingCandidates(0, i - 1, currentAxiom - 1)) {
                    temp = true;
                }
            }
            i++;
        }
        return temp;
    }

    /**
     * Checks which Negs fail ax and updates the pointer.
     *
     * @param ax
     */
    protected static void computeNegs(CandidateAxiom ax) {
        if (ax.consistent) {
            for (int negPr = 0; negPr < negPrograms.size(); negPr++) {
                Program negProgram = negPrograms.get(negPr);
                if (!checkCandidate(ax, negProgram)) {
                    ax.neg[negPr]=Consistent.INCONSISTENT;
                    ax.relevant = true;
                    if (firstCandidates[negPr] == null) {
                        firstCandidates[negPr] = ax;
                    } else {
                        lastNegCandidates[negPr].next[negPr]=ax;
                    }
                    lastNegCandidates[negPr] = ax;
                } else{
                    ax.neg[negPr]= Consistent.CONSISTENT;
                }
            }
        }
    }

    public static void main(String[] args) throws Z3Exception, IOException {
        Log.setLevel(Level.FINEST);
        ConsoleHandler handler = new ConsoleHandler();
        ctx.setPrintMode(Z3_ast_print_mode.Z3_PRINT_SMTLIB_FULL);

        // Publish this level
        handler.setLevel(Level.FINEST);
        Log.addHandler(handler);
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

        List<String> MCMs = Arrays.asList("sc", "tso", "pso", "rmo", "alpha", "power", "arm");

        Option targetOpt = new Option("t", "target", true, "target MCM");
        targetOpt.setRequired(true);
        options.addOption(targetOpt);

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

        //parse pos tests
        File positiveDir = new File(cmd.getOptionValue("positive"));
        posPrograms = new ArrayList<>(positiveDir.listFiles().length);
        posSolvers = new ArrayList<>(positiveDir.listFiles().length);
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
                s.add(p.encodeDF(ctx));
                s.add(p.ass.encode(ctx));
                s.add(p.encodeCF(ctx));
                s.add(p.encodeDF_RF(ctx));
                s.add(Domain.encode(p, ctx));
            }

        }

        //parse neg tests
        File negativeDir = new File(cmd.getOptionValue("negative"));
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
                s.add(p.encodeDF(ctx));
                s.add(p.ass.encode(ctx));
                s.add(p.encodeCF(ctx));
                s.add(p.encodeDF_RF(ctx));
                s.add(Domain.encode(p, ctx));
            }

        }
        
        firstCandidates=new CandidateAxiom[negPrograms.size()];
        lastNegCandidates=new CandidateAxiom[negPrograms.size()];

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
                    candidates.addCandidates();
                }
            }
        } //Dynamic synthesis:
        else {
            //currentAxioms = new ArrayList<CandidateAxiom>(negPrograms.size());
            Log.log(Level.WARNING, "Dynamic Synthesis:  Pos: {0}. Neg: {1}", new Object[]{posPrograms.size(), negPrograms.size()});
            candidates.addBasicrels();
            boolean temp = false;
            //repeatedly check and expand list:
            while (!temp) {
                //use all new relations as potential starting points for dyn. synthesis
                for (int startingpoint = candidates.size()-1; startingpoint >= candidates.unchecked; startingpoint--) {
                    CandidateAxiom ax = candidates.get(startingpoint);
                    //only use relations that pass all POS and fail at least one NEG
                    if (ax.consistent && ax.relevant) {
                        model.push(ax);
                        //try out all relevant models with that relation:
                        if (dynamicSynthesis(startingpoint)) {
                            System.out.println("Found Model: " + ModelCandidate.write());
                            Log.log(Level.INFO, "Number of enumerated relations: {0}", candidates.size());
                            temp = true;
                        } else {
                            //get the stack empty again:
                            model.pop();
                        }
                    }
                }
                //expand list:
                if (!temp) {
                    candidates.addCandidates();
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
        int firstUncovered=model.getNextPassingNeg();
        if (firstUncovered >= negPrograms.size()) {
            return checkDynamicCurrent();
        }
        CandidateAxiom addingax = firstCandidates[firstUncovered];
        while (addingax != null) {
            if(addingax.position>=startingpoint) return false;
            if(!model.redundand(addingax)){
            model.push(addingax);
            if (dynamicSynthesis(startingpoint)) {
                return true;
            }
            model.pop();
            }
            addingax = addingax.next[firstUncovered];
        }
        return false;
    }

    /**
     * Checks ax for consistency against all progs.
     *
     * @param ax
     * @return true if all pos pass and all neg fail
     */
    protected static boolean checkCandidate(CandidateAxiom ax) {
        for (Program posProgram : posPrograms) {
            if (!Objects.equals(ax.consProg.get(posProgram), Boolean.TRUE)) {
                if (!checkCandidate(ax, posProgram)) {
                    ax.consistent = false;
                    return false;
                }
            }
        }
        ax.consistent = true;
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
        ax.consProg.put(p, sat == Status.SATISFIABLE);
        return (sat == Status.SATISFIABLE);
    }

    /**
     * Sketch based synthesis: Checks the current model for consistency. TODO:
     * make sure some preprocessed neg cases get left out.
     *
     * @return
     */
    private static boolean checkStaticCurrent() {
        ModelCandidate = new Wmm();
        for (int i : current) {
            ModelCandidate.addAxiom(candidates.get(i));
        }
        Log.fine("Checking " + ModelCandidate.write());
        for (Program p : negPrograms) {
            //check if p is already knwon to be inconsistent with one of the axioms, if so we can skip it.
            boolean cons = true;
            for (int i : current) {
                if (candidates.get(i).consProg.get(p) == Boolean.FALSE) {
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
}
