package mousquetaires.languages.converters;

import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;


public interface InputProgram2YtreeConverter {

    YSyntaxTree convert(ParserRuleContext mainRuleContext);
}
