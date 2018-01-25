package mousquetaires.utils.exceptions.ytree;

import mousquetaires.utils.StringUtils;
import org.antlr.v4.runtime.ParserRuleContext;


public class InvalidLvalueException extends ParserException {

    public InvalidLvalueException(ParserRuleContext context, String assigner) {
        super(context, "Cannot parse expression " + StringUtils.wrap(assigner)  + " as l-value (assigner)");
    }
}
