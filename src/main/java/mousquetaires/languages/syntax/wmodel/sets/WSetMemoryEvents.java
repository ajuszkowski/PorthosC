package mousquetaires.languages.syntax.wmodel.sets;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.wmodel.visitors.WmodelVisitor;
import mousquetaires.languages.syntax.xgraph.events.memory.XMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;


public class WSetMemoryEvents extends WSetBase<XMemoryEvent> implements WSetAnnotableEvents {

    public WSetMemoryEvents(CodeLocation origin, ImmutableSet<XMemoryEvent> values) {
        super(origin, values);
    }

    @Override
    public <S> S accept(WmodelVisitor<S> visitor) {
        return visitor.visit(this);
    }
}
