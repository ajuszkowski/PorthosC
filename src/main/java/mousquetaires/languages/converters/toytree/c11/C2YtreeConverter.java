package mousquetaires.languages.converters.toytree.c11;


import mousquetaires.languages.common.citation.CitationService;
import mousquetaires.languages.converters.InputProgram2YtreeConverter;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;


public final class C2YtreeConverter implements InputProgram2YtreeConverter {

    private final CitationService citationService;

    public C2YtreeConverter(CitationService citationService) {
        this.citationService = citationService;
    }

    public YSyntaxTree convert(ParserRuleContext mainRuleContext) {
        C2YtreeConverterVisitor visitor = new C2YtreeConverterVisitor(citationService);
        return (YSyntaxTree) mainRuleContext.accept(visitor);
    }
}
