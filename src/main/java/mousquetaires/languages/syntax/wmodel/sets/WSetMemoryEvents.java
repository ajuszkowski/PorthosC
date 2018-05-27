package mousquetaires.languages.syntax.wmodel.sets;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.wmodel.visitors.WmodelVisitor;
import mousquetaires.languages.syntax.xgraph.events.memory.XMemoryEvent;


public class WSetMemoryEvents extends WSetBase<XMemoryEvent> implements WSetAnnotableEvents {

    public WSetMemoryEvents(Origin origin, ImmutableSet<XMemoryEvent> values) {
        super(origin, values);
    }

    @Override
    public <S> S accept(WmodelVisitor<S> visitor) {
        return visitor.visit(this);
    }
}
