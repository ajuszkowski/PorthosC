package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.common.XType;
import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XRvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;

import java.util.Map;

// TODO: implement the Fake event

public final class XAssertionEvent extends XEventBase implements XComputationEvent {

    private final XBinaryComputationEvent assertion;
    //private final Map<XLocalMemoryUnit, XRvalueMemoryUnit> values;

    public XAssertionEvent(XBinaryComputationEvent assertion) {
        this(assertion.getRefId(), assertion);
    }

    private XAssertionEvent(int refId, XBinaryComputationEvent assertion) {
        super(refId, assertion.getInfo());
        this.assertion = assertion;
    }

    public XBinaryComputationEvent getAssertion() {
        return assertion;
    }

    @Override
    public XType getType() {
        return assertion.getType();
    }

    @Override
    public XAssertionEvent asNodeRef(int refId) {
        return new XAssertionEvent(refId, getAssertion());
    }

    @Override
    public String toString() {
        return "exists (" + getAssertion() + ")";
    }

    @Override
    public <T> T accept(XEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <T> T accept(XMemoryUnitVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
