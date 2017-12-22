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

    /**
     * Creates an acyclic Axiom that has additional information regarding its behaviour towards the reachability tests.
     * @param rel
     */
    public CandidateAxiom(Relation rel) {
                super(rel);
    }
    
    public ArrayList<Consistent> pos;
    public ArrayList<Consistent> neg;
    public HashMap<Program, Boolean> consProg = new HashMap<>();
    public boolean consistent=false;
    
    
}

