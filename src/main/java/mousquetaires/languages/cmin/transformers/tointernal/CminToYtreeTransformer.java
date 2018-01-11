package mousquetaires.languages.cmin.transformers.tointernal;


import mousquetaires.languages.ProgramToYtreeTransformer;
import mousquetaires.languages.internalrepr.YSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;


public final class CminToYtreeTransformer implements ProgramToYtreeTransformer {

    public YSyntaxTree transform(ParserRuleContext mainRuleContext) {
        CminToYtreeTransformerVisitor visitor = new CminToYtreeTransformerVisitor();
        return (YSyntaxTree) mainRuleContext.accept(visitor);
    }
}
