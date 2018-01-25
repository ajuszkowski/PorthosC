package mousquetaires.languages.converters.toytree.c11;


import mousquetaires.languages.converters.toytree.InputProgramToYtreeConverter;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;


public final class C11ToYtreeConverter implements InputProgramToYtreeConverter {

    public YSyntaxTree convert(ParserRuleContext mainRuleContext) {
        C11ToYtreeConverterVisitor visitor = new C11ToYtreeConverterVisitor();
        return (YSyntaxTree) mainRuleContext.accept(visitor);
    }
}
