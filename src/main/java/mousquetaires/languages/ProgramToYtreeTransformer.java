package mousquetaires.languages;

import mousquetaires.languages.internalrepr.YSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;


public interface ProgramToYtreeTransformer {
    YSyntaxTree transform(ParserRuleContext mainRuleContext);
}
