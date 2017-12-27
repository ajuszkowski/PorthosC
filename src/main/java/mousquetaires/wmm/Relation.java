/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousquetaires.wmm;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import mousquetaires.program.Program;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Florian Furbach
 */
public abstract class Relation {
    protected String name;
    protected boolean containsRec;
    protected boolean isnamed=false;
    protected String term;
    protected Set<Relation> namedRelations;
    public String write(){
        if(isnamed) return String.format("%s := %s", name, term);
        else return String.format("%s", name);
    }
    
    /*
    Only use this method befoe relations depending on this one are encoded!!!
    */
    public void setName(String name){
        this.name=name;
        if (!namedRelations.contains(this))namedRelations.add(this);
        isnamed=true;
    }

    public Relation(String name) {
        this.namedRelations = new HashSet<>();
        this.name = name;
        this.term = name;
    }
    public Relation(String name, String term) {
        this.namedRelations = new HashSet<>();
        namedRelations.add(this);
        this.name = name;
        this.term = term;
        isnamed=true;
    }

    public String getName() {
        return name;
    }

    public Set<Relation> getNamedRelations() {
        return namedRelations;
    }
    
    
    
    public abstract BoolExpr encode(Program program, Context ctx);
}
