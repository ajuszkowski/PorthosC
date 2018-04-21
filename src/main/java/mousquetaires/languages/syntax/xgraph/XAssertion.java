package mousquetaires.languages.syntax.xgraph;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;

// wrapper over computation event, inherits XMemoryUnit just for simplicity of visiting, etc.
public class XAssertion implements XMemoryUnit {

    private final XComputationEvent assertion;

    public XAssertion(XComputationEvent assertion) {
        this.assertion = assertion;
    }

    public XComputationEvent getAssertion() {
        return assertion;
    }

    @Override
    public Type getType() {
        return assertion.getType();
    }

    @Override
    public <T> T accept(XMemoryUnitVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
