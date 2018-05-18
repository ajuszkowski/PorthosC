package mousquetaires.languages.syntax.wmodel.sets;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.wmodel.visitors.WmodelVisitor;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XInitialWriteEvent;


public class WSetInitialWrites extends WSetBase<XInitialWriteEvent> {

    public WSetInitialWrites(CodeLocation origin, ImmutableSet<XInitialWriteEvent> values) {
        super(origin, values);
    }

    @Override
    public <S> S accept(WmodelVisitor<S> visitor) {
        return visitor.visit(this);
    }
}
