package mousquetaires.languages.transformers;

import mousquetaires.program.Program;
import org.antlr.v4.runtime.ParserRuleContext;


public interface ISyntaxTreeToProgramTransformer {

    Program transform(ParserRuleContext parserRuleContext);
}
