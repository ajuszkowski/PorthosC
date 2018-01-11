package mousquetaires.languages.transformers;

import mousquetaires.languages.ytree.YSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;


public interface ProgramToYtreeTransformer {

    YSyntaxTree transform(ParserRuleContext mainRuleContext);
}
