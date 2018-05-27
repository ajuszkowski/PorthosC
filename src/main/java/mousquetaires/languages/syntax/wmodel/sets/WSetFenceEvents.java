package mousquetaires.languages.syntax.wmodel.sets;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.wmodel.visitors.WmodelVisitor;
import mousquetaires.languages.syntax.xgraph.events.barrier.XBarrierEvent;


public class WSetFenceEvents extends WSetBase<XBarrierEvent> implements WSetAnnotableEvents {

    public WSetFenceEvents(Origin origin, ImmutableSet<XBarrierEvent> values) {
        super(origin, values);
    }

    @Override
    public <S> S accept(WmodelVisitor<S> visitor) {
        return visitor.visit(this);
    }
}
