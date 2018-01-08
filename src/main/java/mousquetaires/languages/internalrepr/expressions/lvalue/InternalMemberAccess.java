package mousquetaires.languages.internalrepr.expressions.lvalue;

public class InternalMemberAccess extends InternalVariableRef {

    public final InternalMemberAccess receiver;

    public InternalMemberAccess(InternalMemberAccess receiver, String memberName) {
        super(memberName);
        this.receiver = receiver;
    }
}
