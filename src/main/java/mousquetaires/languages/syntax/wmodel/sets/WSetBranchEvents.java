package mousquetaires.languages.syntax.wmodel.sets;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.wmodel.visitors.WmodelVisitor;
import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;


public class WSetBranchEvents extends WSetBase<XComputationEvent> implements WSetAnnotableEvents {

    public WSetBranchEvents(CodeLocation origin, ImmutableSet<XComputationEvent> values) {
        super(origin, values);
    }

    @Override
    public <S> S accept(WmodelVisitor<S> visitor) {
        return visitor.visit(this);
    }
}
