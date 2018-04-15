package mousquetaires.utils.exceptions.ytree;

import mousquetaires.utils.AntlrUtils;
import mousquetaires.utils.StringUtils;
import org.antlr.v4.runtime.ParserRuleContext;


public class YParserException extends RuntimeException {

    public YParserException(ParserRuleContext context) {
        this(context, "Undescribed parser error");
    }

    public YParserException(ParserRuleContext context, String info) {
        //todo: retrieve location + original code from context
        // TODO: after parser is almost ready, replace some Y..NotImplException with this exception
        super("Parser error while processing " + StringUtils.wrap(AntlrUtils.getText(context)) + "\n" + info);
    }
}
