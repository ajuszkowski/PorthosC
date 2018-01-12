package mousquetaires.languages.ytree.expressions;

import mousquetaires.languages.visitors.YtreeVisitor;


public class YMemberAccess extends YVariableRef {

    public final YMemberAccess receiver;

    public YMemberAccess(YMemberAccess receiver, String memberName) {
        super(memberName);
        this.receiver = receiver;
    }

    @Override
    public void accept(YtreeVisitor visitor) {
        visitor.visit(this);
    }
}
