package mousquetaires.languages.cmin.transformer;


import mousquetaires.languages.ProgramToYtreeTransformer;
import mousquetaires.languages.ytree.YSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;


public final class CminToYtreeTransformer implements ProgramToYtreeTransformer {

    public YSyntaxTree transform(ParserRuleContext parserRuleContext) {
        CminToYtreeTransformerVisitor visitor = new CminToYtreeTransformerVisitor();
        return (YSyntaxTree) parserRuleContext.accept(visitor);
    }
}
