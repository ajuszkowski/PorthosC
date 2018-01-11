package mousquetaires.languages.transformers.cmin;


import mousquetaires.languages.xrepr.XProgram;
import mousquetaires.languages.ytree.YSyntaxTree;


public final class CminToEventsTransformer  {

    public XProgram transform(YSyntaxTree syntaxTree) {
        CminToEventsTransformerVisitor visitor = new CminToEventsTransformerVisitor();
        return null;
    }
}
