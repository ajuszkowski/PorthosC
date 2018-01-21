package mousquetaires.utils.exceptions.ytree;

import mousquetaires.languages.syntax.ytree.YEntity;


public class InvalidLvalueException extends ParserException {

    public InvalidLvalueException(YEntity assigner) {
        super("Cannot parse expression '" + assigner + "' as l-value (assigner)");
    }
}
