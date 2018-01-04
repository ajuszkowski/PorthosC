package mousquetaires.languages.porthos;

import mousquetaires.execution.Programme;
import mousquetaires.execution.ProgrammeBuilder;
import mousquetaires.interpretation.Interpreter;
import mousquetaires.languages.parsers.PorthosBaseListener;
import mousquetaires.languages.SyntaxTreeToInternalTransformer;
import org.antlr.v4.runtime.ParserRuleContext;


public class PorthosToInternalTransformer
        extends PorthosBaseListener
        implements SyntaxTreeToInternalTransformer {

    private final Interpreter interpreter;
    private final ProgrammeBuilder builder = new ProgrammeBuilder();

    public PorthosToInternalTransformer(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public Programme transform(ParserRuleContext parserRuleContext) {
        parserRuleContext.enterRule(this);
        return builder.build();
    }

}
