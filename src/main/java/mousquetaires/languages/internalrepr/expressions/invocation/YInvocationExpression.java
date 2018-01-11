package mousquetaires.languages.internalrepr.expressions.invocation;

import mousquetaires.languages.internalrepr.expressions.YExpression;
import mousquetaires.languages.internalrepr.signatures.MethodSignature;


public class YInvocationExpression extends YExpression {

    public final MethodSignature signature;
    public final YMethodArgument[] arguments;

    public YInvocationExpression(MethodSignature signature,
                                 YMethodArgument[] arguments) {
        this.signature = signature;
        this.arguments = arguments;
    }
}
