package mousquetaires.languages;

import mousquetaires.languages.internalrepr.InternalSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;


public interface SyntaxTreeToInternalTransformer {

    InternalSyntaxTree transform(ParserRuleContext parserRuleContext);
}
