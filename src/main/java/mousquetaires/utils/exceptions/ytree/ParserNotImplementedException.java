package mousquetaires.utils.exceptions.ytree;

import mousquetaires.utils.StringUtils;
import org.antlr.v4.runtime.ParserRuleContext;


public class ParserNotImplementedException extends ParserException {

    public ParserNotImplementedException(ParserRuleContext context) {
        super(context, "\n" + context.getClass().getSimpleName() +
                ", not supported syntax: " + StringUtils.wrap(context.getText()));
    }
}
