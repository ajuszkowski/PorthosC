package porthosc.memorymodels.axioms;

import porthosc.memorymodels.relations.ZRelation;


public class ZCandidateAxiom extends ZAcyclicAxiom {

    //denotes whether the axiom fails a Neg test.

    public ZConsistent[] pos = new ZConsistent[old.aramis.Aramis.posPrograms.size()];
    public ZConsistent[] neg = new ZConsistent[old.aramis.Aramis.negPrograms.size()];
    //public HashMap<Program, Boolean> consProg = new HashMap<>();
    //Denotes whether the axiom passes all POS tests.
    public boolean consistent = false;
    public int position;
    public ZCandidateAxiom[] next = new ZCandidateAxiom[old.aramis.Aramis.negPrograms.size()];
    public boolean relevant = false;
    /**
     * Creates an acyclic Axiom that has additional information regarding its behaviour towards the reachability src.porthosc.tests.
     *
     * @param rel
     */
    public ZCandidateAxiom(ZRelation rel) {
        super(rel);
    }

    public void largerthan(ZCandidateAxiom ax) {
        for (int i = 0; i < pos.length; i++) {
            if (ax.pos[i] == ZConsistent.INCONSISTENT) {
                consistent = false;
                pos[i] = ax.pos[i];
            }
        }
        for (int i = 0; i < neg.length; i++) {
            if (ax.neg[i] == ZConsistent.INCONSISTENT) {
                neg[i] = ax.neg[i];
            }
        }
    }


    public void smallerthan(ZCandidateAxiom ax) {
        consistent = (consistent || ax.consistent);
        for (int i = 0; i < pos.length; i++) {
            if (ax.pos[i] == ZConsistent.CONSISTENT) {
                pos[i] = ax.pos[i];
            }
        }
        for (int i = 0; i < neg.length; i++) {
            if (ax.neg[i] == ZConsistent.CONSISTENT) {
                neg[i] = ax.neg[i];
            }
        }
    }

    /**
     * This provides the next NEG test we have to cover in the dynamic synthesis.
     *
     * @param firstUncovered
     * @return the index of the first NEG test that passes starting at firstUncovered
     */
    public int getNextpass(int firstUncovered) {
        for (int negprog = firstUncovered; negprog < old.aramis.Aramis.negPrograms.size(); negprog++) {
            if (neg[negprog] != ZConsistent.CONSISTENT) {
                return negprog;
            }
        }
        return old.aramis.Aramis.negPrograms.size();
    }

}

