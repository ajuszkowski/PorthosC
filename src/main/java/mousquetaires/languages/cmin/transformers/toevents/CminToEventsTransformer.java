package mousquetaires.languages.cmin.transformers.toevents;


import mousquetaires.languages.ProgramToYtreeTransformer;
import mousquetaires.languages.eventrepr.XProgram;
import mousquetaires.languages.internalrepr.YSyntaxTree;


public final class CminToEventsTransformer  {

    public XProgram transform(YSyntaxTree syntaxTree) {
        CminToEventsTransformerVisitor visitor = new CminToEventsTransformerVisitor();
        return null;
    }
}
