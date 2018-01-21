package mousquetaires.utils.exceptions.ytree;

public class InvalidRvalueException extends ParserException {

    public InvalidRvalueException(String assignee) {
        super("Cannot parse expression '" + assignee + "' as r-value (assignee)");
    }
}
