package mousquetaires.languages.syntax.xgraph.events.computation;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import mousquetaires.languages.common.Type;
import mousquetaires.languages.syntax.xgraph.events.XEventBase;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;


public class XAssertionEvent extends XEventBase implements XComputationEvent {

    private final XBinaryComputationEvent assertion;

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
    public Type getType() {
        return assertion.getType();
    }

    @Override
    public BoolExpr executes(Context ctx) {
        throw new IllegalStateException();
    }

    @Override
    public String repr() {
        throw new IllegalStateException();
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
