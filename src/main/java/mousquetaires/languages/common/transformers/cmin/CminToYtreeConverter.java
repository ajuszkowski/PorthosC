package mousquetaires.languages.common.transformers.cmin;


import mousquetaires.languages.common.transformers.ProgramToYtreeConverter;
import mousquetaires.languages.ytree.YSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;


public final class CminToYtreeConverter implements ProgramToYtreeConverter {

    public YSyntaxTree convert(ParserRuleContext mainRuleContext) {
        CminToYtreeConverterVisitor visitor = new CminToYtreeConverterVisitor();
        return (YSyntaxTree) mainRuleContext.accept(visitor);
    }
}
