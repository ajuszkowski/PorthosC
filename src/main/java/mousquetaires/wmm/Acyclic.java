/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousquetaires.wmm;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import mousquetaires.program.Event;
import mousquetaires.program.MemEvent;
import mousquetaires.program.Program;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Florian Furbach
 */
public class Acyclic extends Axiom{

    @Override
    public BoolExpr Consistent(Set<Event> events, Context ctx) {
        return Encodings.satAcyclic(rel.getName(), events, ctx);    }

    @Override
    public BoolExpr Inconsistent(Set<Event> events, Context ctx) {
        return ctx.mkAnd(Encodings.satCycleDef(rel.getName(), events, ctx), Encodings.satCycle(rel.getName(), events, ctx));
    }



    public Acyclic(Relation rel) {
        super(rel);
    }

    @Override
    public String write() {
        return String.format("Acyclic(%s)", rel.getName());
    }
    
    
    
}
