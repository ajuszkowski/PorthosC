package mousquetaires.languages.syntax.wmodel.relations;

import com.google.common.collect.ImmutableMap;
import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.wmodel.visitors.WmodelVisitor;
import mousquetaires.languages.syntax.xgraph.events.XEvent;


public class WRelationProgramOrder extends WRelationBase implements WRelationStatic {

    public WRelationProgramOrder(CodeLocation origin, ImmutableMap<XEvent, XEvent> values) {
        super(origin, "po", false, values);
    }

    @Override
    public <T> T accept(WmodelVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
