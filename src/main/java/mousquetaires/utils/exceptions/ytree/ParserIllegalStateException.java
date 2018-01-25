package mousquetaires.utils.exceptions.ytree;

import org.antlr.v4.runtime.ParserRuleContext;


public class ParserIllegalStateException extends ParserException {

    public ParserIllegalStateException(ParserRuleContext context) {
        this(context, "Undescribed parser error");
    }

    public ParserIllegalStateException(ParserRuleContext context, String additionalMessage) {
        super(context, "Parser error: " + additionalMessage);
    }
}
