package mousquetaires.languages.syntax.wmodel.relations;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.wmodel.visitors.WmodelVisitor;
import mousquetaires.languages.syntax.xgraph.events.XEvent;


public class WRelationReadFrom extends WRelationBase implements WRelationStatic {

    public WRelationReadFrom(CodeLocation origin, ImmutableMap<XEvent, XEvent> values) {
        super(origin, "rf", false, values);
    }

    @Override
    public <T> T accept(WmodelVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
