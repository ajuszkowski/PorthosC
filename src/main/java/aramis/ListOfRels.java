/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aramis;

import static aramis.Aramis.computeNegs;
import dartagnan.program.Program;
import dartagnan.wmm.BasicRelation;
import dartagnan.wmm.CandidateAxiom;
import dartagnan.wmm.RelComposition;
import dartagnan.wmm.RelInterSect;
import dartagnan.wmm.RelMinus;
import dartagnan.wmm.RelTrans;
import dartagnan.wmm.RelTransRef;
import dartagnan.wmm.RelUnion;
import dartagnan.wmm.Relation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Florian Furbach
 */
public class ListOfRels {

    private final ArrayList<CandidateAxiom> candidates = new ArrayList<>();
    private final Logger log = Logger.getLogger(ListOfRels.class.getName());
    public int unchecked = 0;

    public ListOfRels() {
                log.setLevel(Level.FINEST);
        ConsoleHandler handler = new ConsoleHandler();

        // Publish this level
        handler.setLevel(Level.FINEST);
        log.addHandler(handler);
    }

    
    /**
     * Adds a relation without consistency knowledge
     *
     * @param rel
     */
    public void add(Relation rel) {
        CandidateAxiom ax = new CandidateAxiom(rel);
        Aramis.checkCandidate(ax);
        ax.position = candidates.size();
        candidates.add(ax);
        log.log(Level.FINE, "Adding and Checking{0}. Consistent: {1}", new Object[]{rel.getName(), ax.consistent});
        Aramis.computeNegs(ax);

    }

    /**
     * TODO: add code
     *
     * @param rel
     * @param map
     */
    public void add(Relation rel, HashMap<Program, Boolean> map) {
        CandidateAxiom ax = new CandidateAxiom(rel);
        ax.consProg = map;
        Aramis.checkCandidate(ax);
        ax.position = candidates.size();
        candidates.add(ax);
        Aramis.computeNegs(ax);
        log.log(Level.FINE, "Adding and Checking{0}. Consistent: {1}", new Object[]{rel.getName(), ax.consistent});
    }

    /**
     * Adds a relation with consistency information.
     *
     * @param rel
     * @param cons denotes whether all POS tests pass.
     */
    public void add(Relation rel, boolean cons) {
        CandidateAxiom ax = new CandidateAxiom(rel);
        ax.consistent = cons;
        ax.position = candidates.size();
        candidates.add(ax);
        Aramis.computeNegs(ax);
        log.fine("Adding " + rel.getName() + ", Consistent: " + cons);
    }

    /**
     * Adds the basic relations add the beginning.
     */
    public void addBasicrels() {
        log.fine("Adding basic relations...");
        add(new BasicRelation("co"));
        add(new BasicRelation("po"));
        add(new BasicRelation("fr"));
        add(new BasicRelation("rf"));
        //add(new BasicRelation("poloc"));
        //add(new BasicRelation("mfence"));
        //add(new BasicRelation("rfe"));
        //add(new BasicRelation("WR")));

    }

    /**
     * Combines all old relations with all relations and adds them to the list
     * and computes their information.
     */
    public void addCandidates() {
        int oldsize = candidates.size();
        for (int j = unchecked; j < oldsize; j++) {
            Relation r1 = candidates.get(j).getRel();
            //candidates.get(j).consProg.fir
            boolean consr1 = candidates.get(j).consistent;
            Map<Program, Boolean> r1consProg = candidates.get(j).consProg;
            if (!(r1 instanceof RelTransRef)) {
                //add(new RelTransRef(r1), consr1);
            }
            if (!(r1 instanceof RelTransRef) && !(r1 instanceof RelTrans)) {
                //add(new RelTrans(r1), consr1);
            }
            if (!(r1 instanceof RelMinus)) {
                add(new RelMinus(r1, new BasicRelation("WR")), consr1);
            }

            for (int i = 0; i < j; i++) {
                //       if(i!=j){
                Relation r2 = candidates.get(i).getRel();
                boolean consr2 = candidates.get(i).consistent;
                Map<Program, Boolean> r2consProg = candidates.get(i).consProg;

                //unions are always added from the left    
                if (!(r2 instanceof RelUnion)) {
                    if (!consr1 || !consr2) {

                        add(new RelUnion(r1, r2), false);
                    } else {
                        add(new RelUnion(r1, r2));
                    }
                }
                boolean unionCons = candidates.get(candidates.size() - 1).consistent;
                HashMap<Program, Boolean> unionProgCons = candidates.get(candidates.size() - 1).consProg;

                //intersections are always added from the left    
                if (!(r2 instanceof RelInterSect)) {
                    if (consr1 && consr2) {
                        add(new RelInterSect(r1, r2), true);
                    } else {
                        HashMap<Program, Boolean> tempmap = new HashMap<>();
                        for (Map.Entry<Program, Boolean> entry : r2consProg.entrySet()) {
                            Program key = entry.getKey();
                            Boolean value = entry.getValue();
                            if (value = Boolean.TRUE) {
                                if (r1consProg.get(key) == Boolean.TRUE) {
                                    tempmap.put(key, Boolean.TRUE);
                                }
                            }
                        }
                        add(new RelInterSect(r1, r2), tempmap);
                    }
                }
                boolean intersectCons = candidates.get(candidates.size() - 1).consistent;

                if (!(r2 instanceof RelComposition)) {
                    if (unionCons) {
                        add(new RelComposition(r1, r2), true);
                        add(new RelComposition(r2, r1), true);

                    } else if (!intersectCons) {
                        add(new RelComposition(r1, r2), false);//add unionProgCons
                        add(new RelComposition(r2, r1), false);//add unionProgCons
                    } else {
                        add(new RelComposition(r1, r2));
                        add(new RelComposition(r2, r1));
                    }
                }
                //  }
            }
        }
        unchecked = oldsize;
    }

    protected CandidateAxiom get(int i) {
        return candidates.get(i);
    }

    protected int size() {
        return candidates.size();
    }

}
