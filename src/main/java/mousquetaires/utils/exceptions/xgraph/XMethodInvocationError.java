package mousquetaires.utils.exceptions.xgraph;

import static mousquetaires.utils.StringUtils.wrap;


public class XMethodInvocationError extends XInterpretationError {

    public XMethodInvocationError(String methodName, String message) {
        super("Method " + wrap(methodName) + " invocation error: " + message);
    }
}
