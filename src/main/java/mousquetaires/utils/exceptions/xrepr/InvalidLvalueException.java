package mousquetaires.utils.exceptions.xrepr;

import mousquetaires.languages.syntax.xrepr.XEntity;


public class InvalidLvalueException extends XCompilationException {

    public InvalidLvalueException(XEntity actualAssignee) {
        super("Cannot assign value to expression '" + actualAssignee +
                "' of type " + actualAssignee.getClass().getName());
    }
}
