package mousquetaires.languages.transformers;


import mousquetaires.languages.parsers.C11BaseListener;
import mousquetaires.program.Program;
import mousquetaires.program.ProgramBuilder;
import org.antlr.v4.runtime.ParserRuleContext;


public class C11ToProgramTransformer
        extends C11BaseListener
        implements ISyntaxTreeToProgramTransformer {

    private final ProgramBuilder builder = new ProgramBuilder();

    public Program transform(ParserRuleContext parserRuleContext) {
        parserRuleContext.enterRule(this);
        builder.finish();
        return builder.toProgram();
    }


}
