package mousquetaires.languages.transformers;


import mousquetaires.languages.parsers.PorthosBaseListener;
import mousquetaires.languages.parsers.PorthosParser;
import mousquetaires.program.Program;
import mousquetaires.program.ProgramBuilder;
import org.antlr.v4.runtime.ParserRuleContext;


public class PorthosToProgramTransformer
        extends PorthosBaseListener
        implements ISyntaxTreeToProgramTransformer {

    private final ProgramBuilder builder = new ProgramBuilder();

    public Program transform(ParserRuleContext parserRuleContext) {
        parserRuleContext.enterRule(this);
        builder.finish();
        return builder.toProgram();
    }

    @Override
    public void enterArith_expr(PorthosParser.Arith_exprContext ctx) {
        super.enterArith_expr(ctx);

    }
}
