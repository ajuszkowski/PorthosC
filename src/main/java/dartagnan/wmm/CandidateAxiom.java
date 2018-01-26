/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dartagnan.wmm;

import dartagnan.program.Program;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Florian Furbach
 */
public class CandidateAxiom extends Acyclic {

    //denotes whether the axiom fails a Neg test.
    public boolean relevant=false;

    /**
     * Creates an acyclic Axiom that has additional information regarding its behaviour towards the reachability src.mousquetaires.tests.
     * @param rel
     */
    public CandidateAxiom(Relation rel) {
                super(rel);
    }
    
    public Consistent[] pos=new Consistent[aramis.Aramis.posPrograms.size()];
    public Consistent[] neg=new Consistent[aramis.Aramis.negPrograms.size()];
    public HashMap<Program, Boolean> consProg = new HashMap<>();
    //Denotes whether the axiom passes all POS tests.
    public boolean consistent=false;
    public int position;
    public CandidateAxiom[] next=new CandidateAxiom[aramis.Aramis.negPrograms.size()];

    /**
     * This provides the next NEG test we have to cover in the dynamic synthesis.
     * @param firstUncovered
     * @return the index of the first NEG test that passes starting at firstUncovered
     */
    public int getNextpass(int firstUncovered) {
        for (int negprog = firstUncovered; negprog < aramis.Aramis.negPrograms.size(); negprog++) {
            if(neg[negprog]!=Consistent.CONSISTENT) return negprog; 
        }
        return aramis.Aramis.negPrograms.size();
    }
    
}

