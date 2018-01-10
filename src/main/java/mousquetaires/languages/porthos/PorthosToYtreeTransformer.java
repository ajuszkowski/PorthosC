package mousquetaires.languages.porthos;

import mousquetaires.languages.ProgramToYtreeTransformer;
import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.ytree.YSyntaxTreeBuilder;
import mousquetaires.languages.parsers.PorthosBaseListener;
import org.antlr.v4.runtime.ParserRuleContext;


public class PorthosToYtreeTransformer
        extends PorthosBaseListener
        implements ProgramToYtreeTransformer {

    private final YSyntaxTreeBuilder builder = new YSyntaxTreeBuilder();

    public YSyntaxTree transform(ParserRuleContext parserRuleContext) {
        parserRuleContext.enterRule(this);
        return builder.build();
    }

}
