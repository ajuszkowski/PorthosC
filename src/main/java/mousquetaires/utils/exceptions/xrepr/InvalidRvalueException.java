package mousquetaires.utils.exceptions.xrepr;

import mousquetaires.languages.syntax.xrepr.XEntity;


public class InvalidRvalueException extends XCompilationException {

    public InvalidRvalueException(XEntity actualAssignee) {
        super("Cannot assign value '" + actualAssignee +
                "' of type " + actualAssignee.getClass().getName());
    }
}
