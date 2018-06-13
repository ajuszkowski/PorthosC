package porthosc.memorymodels.wmm;

import porthosc.memorymodels.axioms.old.Axiom;
import porthosc.memorymodels.axioms.old.CandidateAxiom;
import porthosc.memorymodels.axioms.old.Consistent;
import porthosc.memorymodels.wmm.old.WmmOld;


public class CandidateModel extends WmmOld {
    
    public int size(){
        return axioms.size();
    }
    
    public void push(CandidateAxiom ax){
        axioms.add(ax);        
    }
    
    public boolean pop(){
        if(axioms.size()<=0)return false;
        else{
            axioms.remove(axioms.size()-1);
            return true;
        }
    }
    
    public int getNextPassingNeg(){
        int neg=0;
        while (true) {            
            boolean temp=true;
            CandidateAxiom axio=(CandidateAxiom) axioms.firstElement();
            if(neg>= old.aramis.Aramis.negPrograms.size()) return neg;
            for (Axiom axiom : axioms) {
                CandidateAxiom ax=(CandidateAxiom) axiom;
                if(ax.neg[neg]==Consistent.INCONSISTENT) temp=false;
            }
            if(temp)return neg;
            neg++;
        }
    }

    /**
     * Checks if addingax covers an earlier neg that is already covered by an axiom with a smaller number.
     * This way we only add axioms in decending order if we have the choice and we don't check models twice.
     * @param addingax the axiom that we consider adding.
     * @return whether the axiom has to be added.
     */
    public boolean redundand(CandidateAxiom addingax) {
        for (int i = 0; i < getNextPassingNeg(); i++) {
            if(addingax.neg[i]==Consistent.INCONSISTENT){
                for (Axiom axiom : axioms) {
                    CandidateAxiom ax=(CandidateAxiom) axiom;
                    if(ax.neg[i]==Consistent.INCONSISTENT && ax.position<=addingax.position) return true;
                }
            }
        }
        return false;
    }
    
}
