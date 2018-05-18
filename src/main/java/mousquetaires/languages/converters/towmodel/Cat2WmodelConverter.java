//package mousquetaires.languages.converters.towmodel;
//
//
//import mousquetaires.languages.common.citation.CodeCitationService;
//import mousquetaires.languages.converters.InputProgram2YtreeConverter;
//import mousquetaires.languages.syntax.wmodel.WMemoryModel;
//import org.antlr.v4.runtime.ParserRuleContext;
//
//
//public final class Cat2WmodelConverter implements InputProgram2YtreeConverter<WMemoryModel> {
//
//    private final CodeCitationService citationService;
//
//    public Cat2WmodelConverter(CodeCitationService citationService) {
//        this.citationService = citationService;
//    }
//
//    public WMemoryModel convert(ParserRuleContext mainRuleContext) {
//        Cat2WmodelConverterVisitor visitor = new Cat2WmodelConverterVisitor(citationService);
//        return (WMemoryModel) mainRuleContext.accept(visitor);
//    }
//}
