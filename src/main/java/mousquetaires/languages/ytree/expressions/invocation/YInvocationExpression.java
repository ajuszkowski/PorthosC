package mousquetaires.languages.ytree.expressions.invocation;

import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.languages.ytree.signatures.MethodSignature;


public class YInvocationExpression extends YExpression {

    public final MethodSignature signature;
    public final YMethodArgument[] arguments;

    public YInvocationExpression(MethodSignature signature,
                                 YMethodArgument[] arguments) {
        this.signature = signature;
        this.arguments = arguments;
    }
}
