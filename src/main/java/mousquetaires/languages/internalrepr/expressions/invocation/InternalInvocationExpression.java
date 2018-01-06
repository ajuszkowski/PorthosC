package mousquetaires.languages.internalrepr.expressions.invocation;

import mousquetaires.languages.internalrepr.expressions.InternalExpression;
import mousquetaires.languages.internalrepr.signatures.MethodSignature;


public class InternalInvocationExpression extends InternalExpression {

    public final MethodSignature signature;
    public final MethodArgument[] arguments;

    public InternalInvocationExpression(MethodSignature signature,
                                        MethodArgument[] arguments) {
        this.signature = signature;
        this.arguments = arguments;
    }
}
