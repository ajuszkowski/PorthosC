package mousquetaires.languages.converters.toytree.cmin;


import mousquetaires.languages.converters.toytree.InputProgramToYtreeConverter;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;


public final class CminToYtreeConverter implements InputProgramToYtreeConverter {

    public YSyntaxTree convert(ParserRuleContext mainRuleContext) {
        CminToYtreeConverterVisitor visitor = new CminToYtreeConverterVisitor();
        return (YSyntaxTree) mainRuleContext.accept(visitor);
    }
}
