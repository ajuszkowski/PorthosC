package mousquetaires.languages.porthos;

import mousquetaires.languages.ProgramToYtreeTransformer;
import mousquetaires.languages.ytree.InternalSyntaxTree;
import mousquetaires.languages.ytree.InternalSyntaxTreeBuilder;
import mousquetaires.languages.parsers.PorthosBaseListener;
import org.antlr.v4.runtime.ParserRuleContext;


public class PorthosToYtreeTransformer
        extends PorthosBaseListener
        implements ProgramToYtreeTransformer {

    private final InternalSyntaxTreeBuilder builder = new InternalSyntaxTreeBuilder();

    public InternalSyntaxTree transform(ParserRuleContext parserRuleContext) {
        parserRuleContext.enterRule(this);
        return builder.build();
    }

}
