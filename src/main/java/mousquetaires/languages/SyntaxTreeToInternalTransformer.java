package mousquetaires.languages;

import mousquetaires.execution.Programme;
import org.antlr.v4.runtime.ParserRuleContext;


public interface SyntaxTreeToInternalTransformer {

    Programme transform(ParserRuleContext parserRuleContext);
}
