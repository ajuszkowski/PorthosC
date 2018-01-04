package mousquetaires.languages.internal.expressions.invocation;

import mousquetaires.languages.internal.expressions.Expression;
import mousquetaires.languages.internal.signatures.MethodSignature;


public class InvocationExpression extends Expression {

    public final MethodSignature signature;
    public final MethodArgument[] arguments;

    public InvocationExpression(String originalExpression,
                                MethodSignature signature,
                                MethodArgument[] arguments) {
        super(originalExpression);
        this.signature = signature;
        this.arguments = arguments;
    }
}
