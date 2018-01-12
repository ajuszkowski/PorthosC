package mousquetaires.interpretation.internalrepr.exceptions;

import mousquetaires.languages.xrepr.XEntity;


public class InvalidRvalueException extends InterpreterException {

    public InvalidRvalueException(XEntity actualAssignee) {
        super("Cannot assign value '" + actualAssignee +
                "' of type " + actualAssignee.getClass().getName());
    }
}
