package mousquetaires.languages;

import mousquetaires.languages.ytree.InternalSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;


public interface ProgramToYtreeTransformer {

    InternalSyntaxTree transform(ParserRuleContext parserRuleContext);
}
