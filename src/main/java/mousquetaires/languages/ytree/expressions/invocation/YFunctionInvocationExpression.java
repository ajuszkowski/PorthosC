package mousquetaires.languages.ytree.expressions.invocation;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.languages.ytree.signatures.MethodSignature;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.List;


public class YFunctionInvocationExpression extends YExpression {

    public final MethodSignature signature;
    public final YExpression receiver;
    public final YFunctionArgument[] arguments;

    public YFunctionInvocationExpression(MethodSignature signature,
                                         YExpression receiver,
                                         YFunctionArgument[] arguments) {
        this.signature = signature;
        this.receiver = receiver;
        this.arguments = arguments;
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(receiver, arguments);
    }

    @Override
    public void accept(YtreeVisitor visitor) {
        visitor.visit(this);
    }
}
