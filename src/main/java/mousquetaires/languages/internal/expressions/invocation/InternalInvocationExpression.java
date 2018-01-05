package mousquetaires.languages.internal.expressions.invocation;

import mousquetaires.languages.internal.expressions.InternalExpression;
import mousquetaires.languages.internal.signatures.MethodSignature;


public class InternalInvocationExpression extends InternalExpression {

    public final MethodSignature signature;
    public final MethodArgument[] arguments;

    public InternalInvocationExpression(String originalExpression,
                                        MethodSignature signature,
                                        MethodArgument[] arguments) {
        super(originalExpression);
        this.signature = signature;
        this.arguments = arguments;
    }
}
