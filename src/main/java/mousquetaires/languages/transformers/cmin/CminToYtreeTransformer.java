package mousquetaires.languages.transformers.cmin;


import mousquetaires.languages.transformers.ProgramToYtreeTransformer;
import mousquetaires.languages.ytree.YSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;


public final class CminToYtreeTransformer implements ProgramToYtreeTransformer {

    public YSyntaxTree transform(ParserRuleContext mainRuleContext) {
        CminToYtreeTransformerVisitor visitor = new CminToYtreeTransformerVisitor();
        return (YSyntaxTree) mainRuleContext.accept(visitor);
    }
}
