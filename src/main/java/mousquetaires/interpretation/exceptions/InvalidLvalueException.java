package mousquetaires.interpretation.exceptions;

import mousquetaires.languages.xrepr.XEntity;


public class InvalidLvalueException extends InterpreterException {

    public InvalidLvalueException(XEntity actualAssignee) {
        super("Cannot assign value to expression '" + actualAssignee +
                "' of type " + actualAssignee.getClass().getName());
    }
}
