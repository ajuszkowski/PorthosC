package mousquetaires.languages.porthos;

import mousquetaires.interpretation.eventrepr.Interpreter;
import mousquetaires.languages.SyntaxTreeToInternalTransformer;
import mousquetaires.languages.internalrepr.InternalSyntaxTree;
import mousquetaires.languages.internalrepr.InternalSyntaxTreeBuilder;
import mousquetaires.languages.parsers.PorthosBaseListener;
import org.antlr.v4.runtime.ParserRuleContext;


public class PorthosToInternalTransformer
        extends PorthosBaseListener
        implements SyntaxTreeToInternalTransformer {

    private final Interpreter interpreter;
    private final InternalSyntaxTreeBuilder builder = new InternalSyntaxTreeBuilder();

    public PorthosToInternalTransformer(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public InternalSyntaxTree transform(ParserRuleContext parserRuleContext) {
        parserRuleContext.enterRule(this);
        return builder.build();
    }

}
