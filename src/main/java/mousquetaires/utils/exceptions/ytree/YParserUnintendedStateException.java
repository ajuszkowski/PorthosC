package mousquetaires.utils.exceptions.ytree;

import org.antlr.v4.runtime.ParserRuleContext;


public class YParserUnintendedStateException extends YParserException {

    // TODO: after parser is almost ready, replace some Y..NotImplException with this exception
    public YParserUnintendedStateException(ParserRuleContext context) {
        super(context, "Unintended parser state has been reached");
    }
}
