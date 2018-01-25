package mousquetaires.utils.exceptions.ytree;

import mousquetaires.utils.StringUtils;
import org.antlr.v4.runtime.ParserRuleContext;


public class InvalidRvalueException extends ParserException {

    public InvalidRvalueException(ParserRuleContext context, String assignee) {
        super(context, "Cannot parse expression " + StringUtils.wrap(assignee) + " as r-value (assignee)");
    }
}
