package mousquetaires.languages.syntax.wmodel.visitors;

import mousquetaires.languages.syntax.wmodel.operators.WOperatorBinary;
import mousquetaires.languages.syntax.wmodel.operators.WOperatorUnary;
import mousquetaires.languages.syntax.wmodel.relations.WRelationProgramOrder;
import mousquetaires.languages.syntax.wmodel.relations.WRelationReadFrom;
import mousquetaires.languages.syntax.wmodel.sets.*;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;


public interface WmodelVisitor<T> {

    // pre-defined sets:

    T visit(WSetMemoryEvents set);

    T visit(WSetWrites set);

    T visit(WSetInitialWrites set);

    T visit(WSetReads set);

    T visit(WSetFenceEvents set);

    T visit(WSetBranchEvents set);


    // --
    // relations:

    T visit(WRelationProgramOrder relation);

    T visit(WRelationReadFrom relation);

    // --
    // operators:

    T visit(WOperatorUnary operator);

    T visit(WOperatorBinary operator);
}
