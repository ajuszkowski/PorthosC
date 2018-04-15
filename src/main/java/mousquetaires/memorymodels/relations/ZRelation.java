package mousquetaires.memorymodels.relations;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import dartagnan.program.Program;

import java.util.HashSet;
import java.util.Set;


public abstract class ZRelation {

    /**
     * Describes whether the encoding process uses an over-approximation which is only suitable for checking consistency, NOT inconsistency.
     */
    public final static boolean Approx=false;
    
    protected String name;
    public boolean containsRec;
    protected boolean isnamed=false;
    protected String term;
    public Set<ZRelation> namedRelations;

    public String write(){
        if(isnamed) return String.format("%s := %s", name, term);
        else return String.format("%s", name);
    }
    
    /*
    Only use this method before relations depending on this one are encoded!!!
    */
    public void setName(String name){
        this.name=name;
        if (!namedRelations.contains(this))namedRelations.add(this);
        isnamed=true;
    }

    public ZRelation(String name) {
        this.namedRelations = new HashSet<>();
        this.name = name;
        this.term = name;
    }
    public ZRelation(String name, String term) {
        this.namedRelations = new HashSet<>();
        namedRelations.add(this);
        this.name = name;
        this.term = term;
        isnamed=true;
    }

    public String getName() {
        return ZTemplateRelation.PREFIX+name;
    }

    public Set<ZRelation> getNamedRelations() {
        return namedRelations;
    }
    
    
    public abstract BoolExpr encode(Program program, Context ctx, Set<String> encodedRels) throws Z3Exception;

    protected abstract BoolExpr encodeBasic(Program program, Context ctx) throws Z3Exception;
    
    public BoolExpr encodeApprox(Program program, Context ctx) throws Z3Exception{
        return this.encodeBasic(program, ctx);
    }
}
