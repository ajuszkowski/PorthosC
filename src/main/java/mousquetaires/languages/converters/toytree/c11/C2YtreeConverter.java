package mousquetaires.languages.converters.toytree.c11;


import mousquetaires.languages.converters.toytree.InputProgram2YtreeConverter;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;


public final class C2YtreeConverter implements InputProgram2YtreeConverter {

    public YSyntaxTree convert(ParserRuleContext mainRuleContext) {
        C2YtreeConverterVisitor visitor = new C2YtreeConverterVisitor();
        return (YSyntaxTree) mainRuleContext.accept(visitor);
    }
}
