package mousquetaires.languages.syntax.wmodel.sets;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.wmodel.visitors.WmodelVisitor;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;


public class WSetReads extends WSetBase<XLoadMemoryEvent> implements WSetAnnotableEvents {

    public WSetReads(Origin origin, ImmutableSet<XLoadMemoryEvent> values) {
        super(origin, values);
    }

    @Override
    public <S> S accept(WmodelVisitor<S> visitor) {
        return visitor.visit(this);
    }
}
