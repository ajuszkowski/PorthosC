package mousquetaires.languages.syntax.wmodel.sets;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.wmodel.visitors.WmodelVisitor;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;


public class WSetWrites extends WSetBase<XStoreMemoryEvent> implements WSetAnnotableEvents {

    public WSetWrites(CodeLocation origin, ImmutableSet<XStoreMemoryEvent> values) {
        super(origin, values);
    }

    @Override
    public <S> S accept(WmodelVisitor<S> visitor) {
        return visitor.visit(this);
    }
}
