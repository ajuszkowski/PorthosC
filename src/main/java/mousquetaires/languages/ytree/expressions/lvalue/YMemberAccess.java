package mousquetaires.languages.ytree.expressions.lvalue;

public class YMemberAccess extends YVariableRef {

    public final YMemberAccess receiver;

    public YMemberAccess(YMemberAccess receiver, String memberName) {
        super(memberName);
        this.receiver = receiver;
    }
}
