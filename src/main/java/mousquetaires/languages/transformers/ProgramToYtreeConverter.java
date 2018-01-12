package mousquetaires.languages.transformers;

import mousquetaires.languages.ytree.YSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;


public interface ProgramToYtreeConverter {

    YSyntaxTree convert(ParserRuleContext mainRuleContext);
}
