package porthosc.languages.converters;

import porthosc.languages.syntax.ytree.YSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;


public interface InputProgram2YtreeConverter {

    YSyntaxTree convert(ParserRuleContext mainRuleContext);
}
