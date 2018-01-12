//package mousquetaires.languages.porthos;
//
//import mousquetaires.languages.transformers.ProgramToYtreeConverter;
//import mousquetaires.languages.ytree.YSyntaxTree;
//import mousquetaires.languages.ytree.YSyntaxTreeBuilder;
//import mousquetaires.languages.parsers.PorthosBaseListener;
//import org.antlr.v4.runtime.ParserRuleContext;
//
//
//public class PorthosToYtreeTransformer
//        extends PorthosBaseListener
//        implements ProgramToYtreeConverter {
//
//    private final YSyntaxTreeBuilder builder = new YSyntaxTreeBuilder();
//
//    public YSyntaxTree convert(ParserRuleContext parserRuleContext) {
//        parserRuleContext.enterRule(this);
//        return builder.build();
//    }
//
//}
